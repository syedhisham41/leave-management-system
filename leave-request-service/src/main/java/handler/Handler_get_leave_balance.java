package handler;

import java.io.IOException;
import java.util.List;

import com.sun.net.httpserver.*;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_employee_created_event;
import dto.DTO_employee_id_and_leave_type;
import dto.DTO_leave_balance;
import exceptions.exception.InvalidEmployeeIdException;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import service.Service_leave_balance;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;

public class Handler_get_leave_balance implements HttpHandler {

	private final Service_leave_balance leave_balance_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_get_leave_balance(Service_leave_balance leave_balance_service, ExceptionMapperManager manager) {
		this.leave_balance_service = leave_balance_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		boolean skipValidation = false;
		DTO_employee_id_and_leave_type employee_id_and_leave_type = null;
		double result = 0;
		List<DTO_leave_balance> leave_balance_list = null;
		boolean getSingleLeaveTypeBalance = false;

		if (!exchange.getRequestMethod().equals("GET")) {
			Utils_handler.APIResponse(exchange, Error_code.INVALID_METHOD);
			return;
		}
		try {

			Headers headers = exchange.getRequestHeaders();

			if (headers.containsKey("X-Internal-Call")) {
				String headerValue = headers.getFirst("X-Internal-Call");
				skipValidation = "true".equalsIgnoreCase(headerValue);
			}

			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);

			String query = exchange.getRequestURI().getRawQuery();

			if (query == null || query.isEmpty())
				throw new InvalidEmployeeIdException("employee_id is not provided", null);

			if (query.contains("leave_type"))
				getSingleLeaveTypeBalance = true;

			if (getSingleLeaveTypeBalance) {
				employee_id_and_leave_type = Utils_handler.parseQueryParamForGetLeaveBalance(query);

				if (!auth.isInternal() && !Utils_auth_access_check.ifEmployeeHasAccess(auth.getJwt(),
						employee_id_and_leave_type.getEmployee_id()))
					throw new UserAccessDeniedException(
							"Action forbidden: insufficient privileges : Employee doesnt have access to check leave balance",
							null);

				result = this.leave_balance_service.getLeaveBalance(employee_id_and_leave_type.getEmployee_id(),
						employee_id_and_leave_type.getLeave_type(), skipValidation);
			} else {
				DTO_employee_created_event employee_id = Utils_handler.parseQueryParamForEmployeeId(query);

				if (!auth.isInternal()
						&& !Utils_auth_access_check.ifEmployeeHasAccess(auth.getJwt(), employee_id.getEmployee_id()))
					throw new UserAccessDeniedException(
							"Action forbidden: insufficient privileges : Employee doesnt have access to check leave balance",
							null);

				leave_balance_list = this.leave_balance_service
						.getAllLeaveBalanceForEmployee(employee_id.getEmployee_id(), skipValidation);
			}
			if (getSingleLeaveTypeBalance && result >= 0) {
				Utils_handler.APIResponse(exchange, Success_code.LEAVE_BALANCE_FETCHED, result);
				return;
			} else if (leave_balance_list != null && !leave_balance_list.isEmpty()) {
				Utils_handler.APIResponse(exchange, Success_code.LEAVE_BALANCE_FETCHED, leave_balance_list);
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
