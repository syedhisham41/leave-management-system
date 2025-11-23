package handler;

import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.DTO_api_response;
import dto.DTO_employee_add_result;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import model.Employee;
import service.Service_employee_add;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;

public class Handler_insert_employee implements HttpHandler {

	private final Service_employee_add emp_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_insert_employee(Service_employee_add emp_service, ExceptionMapperManager manager) {
		this.emp_service = emp_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod();

		if (!method.equalsIgnoreCase("POST")) {
			Utils_handler.APIResponse(exchange, error_code.INVALID_METHOD);
			return;
		}

		Employee emp;
		DTO_employee_add_result result;
		InputStream is = exchange.getRequestBody();
		ObjectMapper om = new ObjectMapper();

		try {
			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);

			if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt()))
				throw new UserAccessDeniedException("Action forbidden: insufficient privileges : requires Admin access",
						null);

			emp = om.readValue(is, Employee.class);
			result = this.emp_service.addEmployee(emp);

			if (result.getEmployee_id() > 0 && !result.isEventFailed()) {
				Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_CREATED, result);
			} else if (result.getEmployee_id() > 0 && result.isEventFailed()) {
				Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_CREATED_EVENT_FAILED, result);
			} else {
				Utils_handler.APIResponse(exchange, error_code.INTERNAL_ERROR);
			}
		} catch (Exception e) {
			DTO_api_response<?> response = exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}
	}
}
