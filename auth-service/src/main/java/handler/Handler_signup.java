package handler;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_user_signup;
import exceptions.mapper.ExceptionMapperManager;
import service.Service_signup;
import utils.handler.Utils_common;
import utils.handler.Utils_handler_signup_validator;

public class Handler_signup implements HttpHandler {

	private final Service_signup signup_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_signup(Service_signup signup_service, ExceptionMapperManager exceptionManager) {
		this.signup_service = signup_service;
		this.exceptionManager = exceptionManager;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		String method = exchange.getRequestMethod();

		if (!method.equalsIgnoreCase("POST")) {
			Utils_common.APIResponse(exchange, Error_code.INVALID_METHOD);
			return;
		}

		InputStream is = exchange.getRequestBody();
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);

		try {
			DTO_user_signup user = om.readValue(is, DTO_user_signup.class);
			Utils_handler_signup_validator.validateSignupParameter(user);
			int result = this.signup_service.signup(user);

			if (result > 0)
				Utils_common.APIResponse(exchange, Success_code.SIGNUP_SUCCESSFUL, result);
			else
				Utils_common.APIResponse(exchange, Error_code.INTERNAL_ERROR);

		} catch (Exception e) {
			DTO_api_response<?> response = exceptionManager.handle(e);
			Utils_common.APIResponse(exchange, response);
		}
	}
}
