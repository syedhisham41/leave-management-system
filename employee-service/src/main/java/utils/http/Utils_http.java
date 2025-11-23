package utils.http;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.*;

public class Utils_http {

	public static void sendJsonResponse(HttpExchange exchange, Object data, int statusCode) throws IOException {

		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		byte[] byteResponse = om.writeValueAsBytes(data);

		exchange.getResponseHeaders().set("Content-Type", "application/json");
		exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:5173");
		exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:8081");
		exchange.sendResponseHeaders(statusCode, byteResponse.length);
		OutputStream os = exchange.getResponseBody();
		os.write(byteResponse);
		os.close();

	}

}
