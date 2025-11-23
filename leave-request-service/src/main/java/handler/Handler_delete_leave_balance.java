package handler;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.*;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_employee_deleted_event;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import service.Service_leave_balance;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;

public class Handler_delete_leave_balance implements HttpHandler {

	private final Service_leave_balance leave_balance_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_delete_leave_balance(Service_leave_balance leave_balance_service, ExceptionMapperManager manager) {
		this.leave_balance_service = leave_balance_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		boolean skipValidation = false;
		if (!exchange.getRequestMethod().equals("POST")) {
			Utils_handler.APIResponse(exchange, Error_code.INVALID_METHOD);
			return;
		}
		try {

			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);

			if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt()))
				throw new UserAccessDeniedException("Action forbidden: insufficient privileges : requires Admin access",
						null);

			Headers headers = exchange.getRequestHeaders();

			if (headers.containsKey("X-Internal-Call")) {
				String headerValue = headers.getFirst("X-Internal-Call");
				skipValidation = "true".equalsIgnoreCase(headerValue);
			}

			DTO_employee_deleted_event employee_id = new DTO_employee_deleted_event();

			ObjectMapper om = new ObjectMapper();
			InputStream is = exchange.getRequestBody();
			employee_id = om.readValue(is, DTO_employee_deleted_event.class);
			int result = this.leave_balance_service.deleteLeaveBalance(employee_id.getEmployee_id(), skipValidation);

			if (result == 6) {
				Utils_handler.APIResponse(exchange, Success_code.LEAVE_BALANCE_DELETED, result);
				return;
			} else {
				Utils_handler.APIResponse(exchange, Error_code.LEAVE_BALANCE_REQUEST_FAILED);
				return;
			}
		} catch (Exception e) {
			DTO_api_response<?> response = this.exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}
	}
}
