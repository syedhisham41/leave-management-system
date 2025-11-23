package handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.DTO_api_response;
import dto.DTO_department_manager;
import exceptions.exception.UnIdentifiedParameterException;
import exceptions.mapper.ExceptionMapperManager;
import service.Service_manager;
import utils.handler.common.Utils_handler;
import utils.handler.validator.Utils_handler_department_validator;
import utils.handler.validator.Utils_handler_employee_validator;

public class Handler_get_manager implements HttpHandler {

	private final Service_manager manager_service;
	private final ExceptionMapperManager manager;

	public Handler_get_manager(Service_manager manager_service, ExceptionMapperManager manager) {
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
					List<DTO_department_manager> manager_list = this.manager_service
							.getManagerByEmployeeId(Integer.parseInt(id));
					Utils_handler.APIResponse(exchange, success_code.MANAGER_FETCHED, manager_list);
					return;
				}

				boolean ifQueryWithDeptId = Arrays.stream(Utils_handler_employee_validator.supportedDepartmentIdKeys)
						.anyMatch(params::containsKey);

				if (ifQueryWithDeptId) {
					String dept_id = Utils_handler_department_validator.getDepartmentIdFromQueryAfterValidation(params);
					List<DTO_department_manager> manager_list = this.manager_service.getManagerByDept(dept_id);
					Utils_handler.APIResponse(exchange, success_code.MANAGER_FETCHED, manager_list);
					return;
				}

			} else {
				System.out.println("query is empty");
				List<DTO_department_manager> manager_list = this.manager_service.getAllManagers();
				if(manager_list!=null && !manager_list.isEmpty())
				Utils_handler.APIResponse(exchange, success_code.MANAGER_FETCHED, manager_list);
				else
					Utils_handler.APIResponse(exchange, success_code.MANAGER_LIST_EMPTY, manager_list);
				return;
			}

		} catch (Exception e) {
			DTO_api_response<?> response = manager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}

	}
}
