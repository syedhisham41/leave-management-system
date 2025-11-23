package utils.handler.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_department_query_params;
import exceptions.exception.UnIdentifiedParameterException;
import utils.http.Utils_http;

public class Utils_handler {

	public static DTO_department_query_params parseDepartmentQueryParams(String query)
			throws UnIdentifiedParameterException, UnsupportedEncodingException {

		if (query == null || query.isEmpty())
			return new DTO_department_query_params();

		DTO_department_query_params query_param_dto = new DTO_department_query_params();

		for (String pair : query.split("&")) {
			String[] kv = pair.split("=");
			switch (kv[0]) {

			case "sortBy":
				query_param_dto
						.setSortBy(java.net.URLDecoder.decode(kv[1].toLowerCase(), StandardCharsets.UTF_8.name()));
				break;

			case "order":
				query_param_dto
						.setOrder(java.net.URLDecoder.decode(kv[1].toLowerCase(), StandardCharsets.UTF_8.name()));
				break;

			case "search":
				query_param_dto.setSearch(java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name()));
				break;

			case "page":
				try {
					query_param_dto.setPage(
							Integer.parseInt(java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name())));
				} catch (NumberFormatException e) {
					throw new UnIdentifiedParameterException("invalid number for 'page' " + kv[1], e);
				}
				break;

			case "limit":
				try {
					query_param_dto.setLimit(
							Integer.parseInt(java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name())));
				} catch (NumberFormatException e) {
					throw new UnIdentifiedParameterException("invalid number for 'limit' " + kv[1], e);
				}
				break;

			default:
				throw new UnIdentifiedParameterException("Parameter is not identified :" + kv[0], null);
			}
		}
		return query_param_dto;
	}

	public static Map<String, String> parseCommonQueryParams(String query) {
		Map<String, String> result = new HashMap<>();
		if (query == null || query.isEmpty())
			return result;

		String[] pairs = query.split("&");
		for (String pair : pairs) {
			String[] kv = pair.split("=", 2); // limit to 2 to allow '=' in value
			if (kv.length == 2) {
				result.put(kv[0].toLowerCase(), kv[1]);
			}
		}
		return result;
	}

	public static <T> void APIResponse(HttpExchange exchange, Error_code.error_code error_code) throws IOException {

		DTO_api_response<T> response = new DTO_api_response<T>();

		response.setHttp_code(error_code.getHttp_code());
		response.setCode(error_code.toString());
		response.setMessage(error_code.getMessage());

		Utils_http.sendJsonResponse(exchange, response, error_code.getHttp_code());
	}

	public static <T> void APIResponse(HttpExchange exchange, Success_code.success_code success_code, T data)
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
