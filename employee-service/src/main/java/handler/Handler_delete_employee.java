package handler;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.DTO_api_response;
import dto.DTO_employee_delete_result;
import exceptions.exception.EmployeeDeleteFailedException;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import service.Service_employee_delete;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;

public class Handler_delete_employee implements HttpHandler {

	private final Service_employee_delete emp_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_delete_employee(Service_employee_delete emp_service, ExceptionMapperManager manager) {
		this.emp_service = emp_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		String method = exchange.getRequestMethod();
		if (!method.equalsIgnoreCase("DELETE")) {
			Utils_handler.APIResponse(exchange, error_code.INVALID_METHOD);
			return;
		}

		DTO_employee_delete_result result;
		try {

			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);

			if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt())) {
				throw new UserAccessDeniedException("Insufficient privileges for the request", null);
			}

			String id = null;
			String path = exchange.getRequestURI().getRawPath();

			if (path != null) {

				String[] path_split = path.split("/");
				if (path_split.length == 4 && !path_split[3].isEmpty())
					id = path_split[3];

				if (id == null)
					throw new EmployeeIdMissingException("'Id' is null or invalid", null);
			} else
				throw new EmployeeIdMissingException("'Id' parameter is not present", null);

			if (auth.getJwt().getClaim("emp_id").asInt() == Integer.parseInt(id))
				throw new EmployeeDeleteFailedException("Cannot delete ADMIN employee", null);

			result = this.emp_service.deleteEmployee(Integer.parseInt(id));

			if (result.getEmployee_id() > 0 && !result.isEventFailed())
				Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_DELETED, result);

			else if (result.getEmployee_id() > 0 && result.isEventFailed()) {
				Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_DELETED_EVENT_FAILED, result);
			}

		} catch (Exception e) {
			DTO_api_response<?> response = new DTO_api_response<>();
			response = exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}
	}
}
