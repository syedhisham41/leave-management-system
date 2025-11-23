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
import dto.DTO_user_login_request;
import dto.DTO_user_login_response;
import exceptions.mapper.ExceptionMapperManager;
import service.Service_login;
import utils.handler.Utils_common;
import utils.handler.Utils_handler_login_validator;

public class Handler_login implements HttpHandler {

	private final Service_login login_service;
	private final ExceptionMapperManager exceptionManager;

	public Handler_login(Service_login login_service, ExceptionMapperManager exceptionManager) {
		this.login_service = login_service;
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
			DTO_user_login_request user = om.readValue(is, DTO_user_login_request.class);
			Utils_handler_login_validator.validateLoginParameter(user);
			DTO_user_login_response response = this.login_service.login(user);

			if (response.getEmp_id() > 0)
				Utils_common.APIResponse(exchange, Success_code.LOGIN_SUCCESSFUL, response);
			else
				Utils_common.APIResponse(exchange, Error_code.INTERNAL_ERROR);

		} catch (Exception e) {
			DTO_api_response<?> response = exceptionManager.handle(e);
			Utils_common.APIResponse(exchange, response);
		}
	}
}
