package handler;

import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.*;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_leave_request;
import exceptions.mapper.ExceptionMapperManager;
import service.Service_leave_request_post;
import utils.handler.common.Utils_handler;
import utils.handler.validator.Utils_handler_leave_request_validator;

public class Handler_post_leave_request implements HttpHandler {

	private final Service_leave_request_post leave_post_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_post_leave_request(Service_leave_request_post leave_post_service, ExceptionMapperManager manager) {
		this.leave_post_service = leave_post_service;
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		DTO_leave_request leave_request = new DTO_leave_request();

		if (!exchange.getRequestMethod().equals("POST")) {
			Utils_handler.APIResponse(exchange, Error_code.INVALID_METHOD);
			return;
		}
		try {
			ObjectMapper om = new ObjectMapper();
			om.registerModule(new JavaTimeModule());
			om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
			InputStream is = exchange.getRequestBody();
			JsonNode node = om.readTree(is);

			Utils_handler_leave_request_validator.leaveRequestValidatorForPost(node);

			leave_request = om.treeToValue(node, DTO_leave_request.class);

			int result = this.leave_post_service.postLeaveRequest(leave_request);

			if (result > 0) {
				Utils_handler.APIResponse(exchange, Success_code.LEAVE_REQUEST_CREATED, result);
			} else {
				Utils_handler.APIResponse(exchange, Error_code.LEAVE_REQUEST_FAILED);
			}
		} catch (Exception e) {
			DTO_api_response<?> response =  this.exceptionManager.handle(e);
			Utils_handler.APIResponse(exchange, response);
	}
}
}
