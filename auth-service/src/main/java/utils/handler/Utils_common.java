package utils.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import utils.http.Utils_http;

public class Utils_common {
	
	public static <T> void APIResponse(HttpExchange exchange, Error_code error_code) throws IOException {

		DTO_api_response<T> response = new DTO_api_response<T>();

		response.setHttp_code(error_code.getHttp_code());
		response.setCode(error_code.toString());
		response.setMessage(error_code.getMessage());

		Utils_http.sendJsonResponse(exchange, response, error_code.getHttp_code());
	}

	public static <T> void APIResponse(HttpExchange exchange,  Success_code success_code, T data)
			throws IOException {

		DTO_api_response<T> response = new DTO_api_response<T>();

		response.setHttp_code(success_code.getHttp_code());
		response.setCode(success_code.toString());
		response.setMessage(success_code.getMessage());
		response.setData(data);

		Utils_http.sendJsonResponse(exchange, response, success_code.getHttp_code());
	}

	public static <T> void APIResponse(HttpExchange exchange, DTO_api_response<T> response) throws IOException {
		Utils_http.sendJsonResponse(exchange, response, response.getHttp_code());
	}

}
