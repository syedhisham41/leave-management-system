package handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.*;
import exceptions.exception.UnIdentifiedParameterException;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import service.Service_employee_get;
import service.Service_manager;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;
import utils.handler.common.Utils_handler_employee;
import utils.handler.validator.Utils_handler_employee_validator;
import utils.handler.validator.Utils_handler_manager_validator;

public class Handler_get_employee implements HttpHandler {

	private final Service_employee_get emp_service;
	private final Service_manager manager_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_get_employee(Service_employee_get emp_service, Service_manager manager_service,
			ExceptionMapperManager manager) {
		this.emp_service = emp_service;
		this.manager_service = manager_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		DTO_employee_query_params qp = null;
		List<DTO_employee> emp_list = null;
		String method = exchange.getRequestMethod();

		try {

			if (!method.equalsIgnoreCase("GET")) {
				Utils_handler.APIResponse(exchange, error_code.INVALID_METHOD);
				return;
			}

			String path = exchange.getRequestURI().getPath();
			if (!path.equals("/employee/get")) {
				Utils_handler.APIResponse(exchange, error_code.INVALID_ENDPOINT);
				return;
			}

			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);

			String query = exchange.getRequestURI().getRawQuery();

			if (query != null && !query.isEmpty()) {
				Map<String, String> params = Utils_handler.parseCommonQueryParams(query);
				if (params.isEmpty()) {
					throw new UnIdentifiedParameterException("Invalid or unrecognized query parameters", null);
				}
				System.out.println(params);
				boolean ifEmployeeWithEmployeeId = Arrays
						.stream(Utils_handler_employee_validator.supportedEmployeeIdKeys).anyMatch(params::containsKey);
				if (ifEmployeeWithEmployeeId) {
					String id = Utils_handler_employee_validator.getEmployeeIdFromQueryAfterValidation(params);
					DTO_employee emp = this.emp_service.getEmployeeByID(Integer.parseInt(id));
					Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_FETCHED, emp);
					return;
				}

				boolean ifEmployeeWithManagerId = Arrays.stream(Utils_handler_employee_validator.supportedManagerIdKeys)
						.anyMatch(params::containsKey);

				if (ifEmployeeWithManagerId) {
					if (!auth.isInternal() && !Utils_auth_access_check.hasManagerOrAdminAccess(auth.getJwt()))
						throw new UserAccessDeniedException(
								"Action forbidden: insufficient privileges : requires Manager/Admin access", null);
					
					qp = Utils_handler_employee.generateEmployeeDtoFromQueryMapForManagerFilter(params);
					String id = Utils_handler_manager_validator.getManagerFromQueryAfterValidation(params);
					emp_list = this.manager_service.getEmployeesByManagerId(Integer.parseInt(id), qp);
					if (emp_list != null && !emp_list.isEmpty())
						Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_FETCHED, emp_list);
					else {
						Utils_handler.APIResponse(exchange, error_code.EMPLOYEE_NOT_FOUND);
					}
					return;
				}

				boolean ifEmployeeWithEmailId = Arrays.stream(Utils_handler_employee_validator.supportedEmailKeys)
						.anyMatch(params::containsKey);
				if (ifEmployeeWithEmailId) {
					String email = Utils_handler_employee_validator.getEmailIdKeyFromQueryAfterValidation(params);
					DTO_employee emp = this.emp_service.getEmployeeByEmailId(email);
					Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_FETCHED, emp);
					return;
				}

				qp = Utils_handler_employee.generateEmployeeDtoFromQueryMap(params);
				emp_list = this.emp_service.getEmployeesByQuery(qp);
				if (emp_list != null && !emp_list.isEmpty())
					Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_FETCHED, emp_list);
				else {
					Utils_handler.APIResponse(exchange, error_code.EMPLOYEE_NOT_FOUND);
				}
				return;

			} else {
				if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt()))
					throw new UserAccessDeniedException(
							"Action forbidden: insufficient privileges : requires Admin access", null);
				
				System.out.println("query is empty");
				emp_list = this.emp_service.getAllEmployees();
				Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_FETCHED, emp_list);
				return;
			}

		} catch (Exception e) {
			DTO_api_response<?> response = exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}
	}
}
