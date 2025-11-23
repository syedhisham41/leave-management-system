package handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.DTO_api_response;
import dto.DTO_department;
import exceptions.mapper.ExceptionMapperManager;
import service.Service_department;
import utils.handler.common.Utils_handler;
import utils.handler.validator.Utils_handler_department_validator;

public class Handler_get_department implements HttpHandler {

	private final Service_department department_service;
	private final ExceptionMapperManager manager;

	public Handler_get_department(Service_department department_service, ExceptionMapperManager manager) {
		this.department_service = department_service;
		this.manager = manager;
	}

	public void handle(HttpExchange exchange) throws IOException {

		String method = exchange.getRequestMethod();

		if (!method.equalsIgnoreCase("GET")) {
			Utils_handler.APIResponse(exchange, error_code.INVALID_METHOD);
			return;
		}
		
		try {

			String query = exchange.getRequestURI().getRawQuery();
			if (query != null && !query.isEmpty()) {

				Map<String, String> params = Utils_handler.parseCommonQueryParams(query);
				String department_id = Utils_handler_department_validator
						.getDepartmentIdFromQueryAfterValidation(params);
				DTO_department department = this.department_service.getDepartment(department_id);
				Utils_handler.APIResponse(exchange, success_code.DEPARTMENT_FETCHED, department);
				return;
			}

			List<DTO_department> department_list = this.department_service.getAllDepartment();
			if (department_list != null && !department_list.isEmpty()) {
				Utils_handler.APIResponse(exchange, success_code.DEPARTMENT_FETCHED, department_list);
				return;
			} else {
				Utils_handler.APIResponse(exchange, success_code.DEPARTMENT_LIST_EMPTY, department_list);
				return;
			}

		} catch (Exception e) {
			DTO_api_response<?> response = manager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}

	}
}
