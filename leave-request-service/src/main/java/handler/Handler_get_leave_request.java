package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.*;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_leave_request_get;
import exceptions.exception.UnidentifiedParameterException;
import exceptions.exception.UnsupportedQueryParameterException;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import service.Service_leave_request_get;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;
import utils.handler.validator.Utils_handler_leave_request_validator;

public class Handler_get_leave_request implements HttpHandler {

	private final Service_leave_request_get leave_request_get_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_get_leave_request(Service_leave_request_get leave_request_get_service,
			ExceptionMapperManager manager) {
		this.leave_request_get_service = leave_request_get_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		List<DTO_leave_request_get> leave_request_list = new ArrayList<DTO_leave_request_get>();
		int employee_id = 0;
		int manager_id = 0;
		int leave_id = 0;

		if (!exchange.getRequestMethod().equals("GET")) {
			Utils_handler.APIResponse(exchange, Error_code.INVALID_METHOD);
			return;
		}
		try {

			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);
			String query = exchange.getRequestURI().getRawQuery();

			if (query != null && !query.isEmpty()) {
				Map<String, String> params = Utils_handler.parseCommonQueryParams(query);

				if (params.isEmpty()) {
					throw new UnidentifiedParameterException("Invalid or unrecognized query parameters", null);
				}

				System.out.println(params);

				boolean ifWithEmployeeId = Arrays.stream(Utils_handler_leave_request_validator.supportedEmployeeIdKeys)
						.anyMatch(params::containsKey);
				if (ifWithEmployeeId) {
					employee_id = Utils_handler_leave_request_validator.getEmployeeIdFromQueryAfterValidation(params);
					System.out.println("employee_id in getLeaveRequest:" + employee_id);

					if (!auth.isInternal() && !Utils_auth_access_check.ifEmployeeHasAccess(auth.getJwt(), employee_id))
						throw new UserAccessDeniedException(
								"Action forbidden: insufficient privileges : Employee doesnt have access to check leave requests",
								null);

					leave_request_list = this.leave_request_get_service.getLeaveRequestByEmployeeId(employee_id);
					if (leave_request_list != null && !leave_request_list.isEmpty()) {
						Utils_handler.APIResponse(exchange, Success_code.LEAVE_REQUEST_FETCHED, leave_request_list);
					} else {
						Utils_handler.APIResponse(exchange, Error_code.LEAVE_REQUEST_NOT_FOUND);
					}
				}

				boolean ifWithManagerId = Arrays.stream(Utils_handler_leave_request_validator.supportedManagerIdKeys)
						.anyMatch(params::containsKey);
				if (ifWithManagerId) {
					manager_id = Utils_handler_leave_request_validator.getManagerIdFromQueryAfterValidation(params);

					if (!auth.isInternal() && !Utils_auth_access_check.ifManagerHasAccess(auth.getJwt(), manager_id))
						throw new UserAccessDeniedException(
								"Action forbidden: insufficient privileges : Manager doesnt have access to check leave requests",
								null);

					System.out.println("manager_id in getLeaveRequest:" + manager_id);
					leave_request_list = this.leave_request_get_service.getLeaveRequestByManagerId(manager_id);
					if (leave_request_list != null && !leave_request_list.isEmpty()) {
						Utils_handler.APIResponse(exchange, Success_code.LEAVE_REQUEST_FETCHED, leave_request_list);
					} else {
						Utils_handler.APIResponse(exchange, Error_code.LEAVE_REQUEST_NOT_FOUND);
					}
				}

				boolean ifWithLeaveId = Arrays.stream(Utils_handler_leave_request_validator.supportedLeaveIdKeys)
						.anyMatch(params::containsKey);
				if (ifWithLeaveId) {

					if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt()))
						throw new UserAccessDeniedException(
								"Action forbidden: insufficient privileges : requires Admin access", null);

					leave_id = Utils_handler_leave_request_validator.getLeaveIdFromQueryAfterValidation(params);
					System.out.println("leave_id in getLeaveRequest:" + manager_id);
					DTO_leave_request_get leave_request = this.leave_request_get_service
							.getLeaveRequestByLeaveId(leave_id);
					Utils_handler.APIResponse(exchange, Success_code.LEAVE_REQUEST_FETCHED, leave_request);
				}

				System.out.println("unsupported query paramter for the GET request");
				throw new UnsupportedQueryParameterException("Unsupported query paramter for  GET leave request", null);
			} else {

				System.out.println("no query parameters");
				if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt()))
					throw new UserAccessDeniedException(
							"Action forbidden: insufficient privileges : requires Admin access", null);

				leave_request_list = this.leave_request_get_service.getAllLeaveRequest();
				if (leave_request_list != null && !leave_request_list.isEmpty()) {
					Utils_handler.APIResponse(exchange, Success_code.LEAVE_REQUEST_FETCHED, leave_request_list);
				} else {
					Utils_handler.APIResponse(exchange, Error_code.LEAVE_REQUEST_NOT_FOUND);
				}
			}

		} catch (Exception e) {
			DTO_api_response<?> response = this.exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}
	}
}
