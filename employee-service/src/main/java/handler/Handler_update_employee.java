package handler;

import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.DTO_api_response;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import model.Employee;
import service.Service_employee_update;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;

public class Handler_update_employee implements HttpHandler {

	private final Service_employee_update emp_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_update_employee(Service_employee_update emp_service, ExceptionMapperManager manager) {
		this.emp_service = emp_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		String method = exchange.getRequestMethod();
		if (!method.equalsIgnoreCase("PUT")) {
			Utils_handler.APIResponse(exchange, error_code.INVALID_METHOD);
			return;
		}

		try {
			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);
			if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt()))
				throw new UserAccessDeniedException("Action forbidden: insufficient privileges : requires Admin access",
						null);

			String id = null;
			String path = exchange.getRequestURI().getRawPath();

			if (path != null) {

				String[] path_split = path.split("/");
				if (path_split.length == 4 && !path_split[3].isEmpty())
					id = path_split[3];

				if (id == null)
					throw new EmployeeIdMissingException("'Id' field value is null", null);

			} else
				throw new EmployeeIdMissingException("'Id' field value is invalid", null);

			Employee emp;
			InputStream is = exchange.getRequestBody();
			ObjectMapper om = new ObjectMapper();

			emp = om.readValue(is, Employee.class);

			int response = this.emp_service.updateEmployee(Integer.parseInt(id), emp);

			if (response == 1)
				Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_UPDATED, response);

		} catch (Exception e) {
			DTO_api_response<?> response = new DTO_api_response<>();
			response = exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}

	}
}
