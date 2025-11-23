package handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import exceptions.mapper.ExceptionMapperManager;
import utils.handler.common.Utils_handler;

public class Handler_debug_exceptions implements HttpHandler {

	private final ExceptionMapperManager exceptionManager;

	public Handler_debug_exceptions(ExceptionMapperManager manager) {
		this.exceptionManager = manager;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		String method = exchange.getRequestMethod();

		if (!method.equalsIgnoreCase("GET")) {
			Utils_handler.APIResponse(exchange, error_code.INVALID_METHOD);
			return;
		}

		Map<String, Object> debugInfo = new HashMap<>();

		debugInfo.put("isDebug", exceptionManager.isDebug());
		debugInfo.put("Exception Count", exceptionManager.getExceptionCount().get());
		debugInfo.put("Exception List", exceptionManager.getLastExceptions());

		Utils_handler.APIResponse(exchange, success_code.DEBUG_INFO, debugInfo);

	}
}
