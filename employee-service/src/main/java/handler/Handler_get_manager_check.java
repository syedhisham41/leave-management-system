package handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.DTO_api_response;
import dto.DTO_manager_check;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.UnIdentifiedParameterException;
import exceptions.mapper.ExceptionMapperManager;
import service.Service_manager;
import utils.handler.common.Utils_handler;
import utils.handler.validator.Utils_handler_employee_validator;

public class Handler_get_manager_check implements HttpHandler {

	private final Service_manager manager_service;
	private final ExceptionMapperManager manager;

	public Handler_get_manager_check(Service_manager manager_service, ExceptionMapperManager manager) {
		this.manager_service = manager_service;
		this.manager = manager;
	}

	public void handle(HttpExchange exchange) throws IOException {

		String method = exchange.getRequestMethod();

		if (!method.equalsIgnoreCase("GET")) {
			Utils_handler.APIResponse(exchange, error_code.INVALID_METHOD);
			return;
		}

		String query = exchange.getRequestURI().getRawQuery();

		try {
			if (query != null && !query.isEmpty()) {
				Map<String, String> params = Utils_handler.parseCommonQueryParams(query);
				if (params.isEmpty()) {
					throw new UnIdentifiedParameterException("Invalid or unrecognized query parameters", null);
				}
				System.out.println(params);
				boolean ifQueryWithEmployeeId = Arrays.stream(Utils_handler_employee_validator.supportedEmployeeIdKeys)
						.anyMatch(params::containsKey);
				if (ifQueryWithEmployeeId) {
					String id = Utils_handler_employee_validator.getEmployeeIdFromQueryAfterValidation(params);
					DTO_manager_check manager_check_dto = this.manager_service
							.getManagerCheckInfo(Integer.parseInt(id));
					if(manager_check_dto.getIsManager()) {
						Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_IS_MANAGER, manager_check_dto);
						return;
					}
					else {
						Utils_handler.APIResponse(exchange, success_code.EMPLOYEE_IS_NOT_MANAGER, manager_check_dto);
						return;
					}
					
				}

				throw new UnIdentifiedParameterException("Invalid query parameter", null);

			} else {
				System.out.println("query is empty");
				throw new EmployeeIdMissingException("Employee id is not provided to check whether manager or not",
						null);
			}

		} catch (Exception e) {
			DTO_api_response<?> response = manager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}

	}
}
