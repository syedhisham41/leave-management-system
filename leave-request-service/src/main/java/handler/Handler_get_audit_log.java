package handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.*;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_leave_audit_log;
import exceptions.exception.LeaveAuditLogNotFoundException;
import exceptions.exception.UnsupportedQueryParameterException;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import service.Service_leave_audit_log;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;
import utils.handler.validator.Utils_handler_leave_audit_validator;
import utils.handler.validator.Utils_handler_leave_request_validator;

public class Handler_get_audit_log implements HttpHandler {

	private final Service_leave_audit_log leave_audit_log_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_get_audit_log(Service_leave_audit_log leave_audit_log_service, ExceptionMapperManager manager) {
		this.leave_audit_log_service = leave_audit_log_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		boolean manager = false;
		if (!exchange.getRequestMethod().equals("GET")) {
			Utils_handler.APIResponse(exchange, Error_code.INVALID_METHOD);
			return;
		}

		try {

			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);

			String query = exchange.getRequestURI().getRawQuery();

			if (query != null && !query.isEmpty()) {
				Map<String, String> params = Utils_handler.parseCommonQueryParams(query);

				System.out.println(params);

				Utils_handler.validateAllowedParams(params,
						Utils_handler_leave_audit_validator.supportedManagerCheckKeys,
						Utils_handler_leave_request_validator.supportedEmployeeIdKeys,
						Utils_handler_leave_request_validator.supportedLeaveIdKeys);

				boolean ifWithEmployeeId = Arrays.stream(Utils_handler_leave_request_validator.supportedEmployeeIdKeys)
						.anyMatch(params::containsKey);
				if (ifWithEmployeeId) {
					boolean ifWithManagerCheck = Arrays
							.stream(Utils_handler_leave_audit_validator.supportedManagerCheckKeys)
							.anyMatch(params::containsKey);

					int employee_id = Utils_handler_leave_request_validator
							.getEmployeeIdFromQueryAfterValidation(params);

					if (!auth.isInternal() && !Utils_auth_access_check.ifEmployeeHasAccess(auth.getJwt(), employee_id))
						throw new UserAccessDeniedException(
								"Action forbidden: insufficient privileges : Employee doesnt have access to check leave audit logs",
								null);

					if (ifWithManagerCheck) {
						manager = Utils_handler_leave_audit_validator.getManagerCheckFromQueryAfterValidation(params);
					}

					List<DTO_leave_audit_log> audit_log_list = this.leave_audit_log_service
							.getAuditLogByEmployeeId(employee_id, manager, true);
					if (audit_log_list != null && !audit_log_list.isEmpty()) {
						Utils_handler.APIResponse(exchange, Success_code.LEAVE_AUDIT_LOG_FETCHED, audit_log_list);
						return;
					}

//					throw new LeaveAuditLogNotFoundException("Leave audit logs not found for the employee_id/manager",
//							null);
				}

				boolean ifWithLeaveId = Arrays.stream(Utils_handler_leave_request_validator.supportedLeaveIdKeys)
						.anyMatch(params::containsKey);

				System.out.println("leave id flag :" + ifWithLeaveId);
				if (ifWithLeaveId) {

					if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt()))
						throw new UserAccessDeniedException(
								"Action forbidden: insufficient privileges : requires Admin Access", null);

					int leave_id = Utils_handler_leave_request_validator.getLeaveIdFromQueryAfterValidation(params);
					DTO_leave_audit_log audit_log_list = this.leave_audit_log_service.getAuditLogByLeaveId(leave_id);
					Utils_handler.APIResponse(exchange, Success_code.LEAVE_AUDIT_LOG_FETCHED, audit_log_list);
					return;
				}

				System.out.println("unsupported query paramter for the GET request");
				throw new UnsupportedQueryParameterException("Unsupported query paramter for  GET Leave Audit Log",
						null);
			}

			if (!auth.isInternal() && !Utils_auth_access_check.hasAdminAccess(auth.getJwt()))
				throw new UserAccessDeniedException("Action forbidden: insufficient privileges : requires Admin Access",
						null);

			List<DTO_leave_audit_log> audit_log_list = this.leave_audit_log_service.getAllAuditLog();

			if (audit_log_list != null && !audit_log_list.isEmpty()) {
				Utils_handler.APIResponse(exchange, Success_code.LEAVE_AUDIT_LOG_FETCHED, audit_log_list);
				return;
			}
			throw new LeaveAuditLogNotFoundException("Leave audit logs table is empty", null);

		} catch (Exception e) {
			DTO_api_response<?> response = this.exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}
	}
}
