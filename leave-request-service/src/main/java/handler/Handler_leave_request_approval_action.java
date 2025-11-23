package handler;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.*;

import constants.enums.Error_code;
import constants.enums.Leave_action;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_leave_request_approval_action;
import exceptions.exception.UnsupportedQueryParameterException;
import exceptions.exception.UserAccessDeniedException;
import exceptions.mapper.ExceptionMapperManager;
import model.AuthResult;
import service.Service_leave_request_approval;
import utils.auth.Utils_auth_access_check;
import utils.auth.Utils_auth_common;
import utils.handler.common.Utils_handler;
import utils.handler.validator.Utils_handler_leave_approval_validator;

public class Handler_leave_request_approval_action implements HttpHandler {

	private final Service_leave_request_approval leave_approval_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_leave_request_approval_action(Service_leave_request_approval leave_approval_service,
			ExceptionMapperManager manager) {
		this.leave_approval_service = leave_approval_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		DTO_leave_request_approval_action leave_approval = new DTO_leave_request_approval_action();

		if (!exchange.getRequestMethod().equals("PUT")) {
			Utils_handler.APIResponse(exchange, Error_code.INVALID_METHOD);
			return;
		}
		try {

			AuthResult auth = Utils_auth_common.authenticateRequest(exchange);

			ObjectMapper om = new ObjectMapper();
			om.registerModule(new JavaTimeModule());
			om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
			InputStream is = exchange.getRequestBody();
			JsonNode node = om.readTree(is);

			Utils_handler_leave_approval_validator.leaveApprovalValidator(node);

			leave_approval = om.treeToValue(node, DTO_leave_request_approval_action.class);

			if (leave_approval.getAction().equals(Leave_action.APPROVED)) {
				if (!auth.isInternal() && !Utils_auth_access_check.hasManagerOrAdminAccess(auth.getJwt()))
					throw new UserAccessDeniedException(
							"Action forbidden: insufficient privileges : Requires Manager/Admin access for Leave approvals",
							null);
				this.leave_approval_service.approveLeaveRequest(leave_approval.getLeave_id(),
						leave_approval.getApprover_id(), leave_approval.getComments());
				Utils_handler.APIResponse(exchange, Success_code.LEAVE_REQUEST_APPROVED, "Leave request Approved");
			}

			else if (leave_approval.getAction().equals(Leave_action.REJECTED)) {
				if (!auth.isInternal() && !Utils_auth_access_check.hasManagerOrAdminAccess(auth.getJwt()))
					throw new UserAccessDeniedException(
							"Action forbidden: insufficient privileges : Requires Manager/Admin access for Leave rejections",
							null);
				this.leave_approval_service.rejectLeaveRequest(leave_approval.getLeave_id(),
						leave_approval.getApprover_id(), leave_approval.getComments());
				Utils_handler.APIResponse(exchange, Success_code.LEAVE_REQUEST_REJECTED, "Leave request Rejected");
			}

			else if (leave_approval.getAction().equals(Leave_action.CANCELLED)) {
				if (!auth.isInternal()
						&& !Utils_auth_access_check.ifEmployeeHasAccess(auth.getJwt(), leave_approval.getApprover_id()))
					throw new UserAccessDeniedException(
							"Action forbidden: insufficient privileges : Employee doesnt have the access to Cancel the leave request",
							null);
				this.leave_approval_service.cancelLeaveRequest(leave_approval.getLeave_id(),
						leave_approval.getApprover_id(), leave_approval.getComments());
				Utils_handler.APIResponse(exchange, Success_code.LEAVE_REQUEST_CANCELLED, "Leave request Cancelled");
			}

			else
				throw new UnsupportedQueryParameterException("unsupported parameter for Leave approval request", null);

		} catch (Exception e) {
			DTO_api_response<?> response = this.exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
		}
	}
}
