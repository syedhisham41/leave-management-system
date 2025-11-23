package utils.service.proxy;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import config.PropertyConfig;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.LeaveServiceUnreachableException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Utils_leave_request_service_proxy {

	private static final int TIMEOUT_MILLIS = 10000;
	private static final String INTERNAL_TOKEN = PropertyConfig.get("SERVICE_SECRET");
	private static final String LEAVE_REQUEST_SERVCE_URL = PropertyConfig.get("LEAVE_REQUEST_SERVCE_URL");

	public static void notifyLeaveRequestOfEmployeeCreated(int employeeId)
			throws EmployeeNotFoundException, DataAccessException, LeaveServiceUnreachableException {

		String url = LEAVE_REQUEST_SERVCE_URL + "leavebalance/create";

		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
				.setResponseTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).build();

		try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build()) {

			HttpPost request = new HttpPost(URI.create(url));
			request.setHeader("Content-Type", "application/json");
			request.setHeader("X-Internal-Call", "true");
			request.addHeader("X-Service-Auth", INTERNAL_TOKEN);

			// JSON body: {"employee_id": 123}
			String jsonBody = "{\"employee_id\":" + employeeId + "}";
			StringEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
			request.setEntity(entity);

			client.execute(request, response -> {
				int statusCode = response.getCode();
				String responseBody = new String(response.getEntity().getContent().readAllBytes(),
						StandardCharsets.UTF_8);

				// Log for debugging
				System.out.println("Leave-request-service response (" + statusCode + "): " + responseBody);

				if (statusCode != 200 && statusCode != 201) {
					throw new RuntimeException(new LeaveServiceUnreachableException(
							"Unexpected HTTP code " + statusCode + " from leave-request-service. Body: " + responseBody,
							null));
				}

				return null; // we don't need to return anything
			});

		} catch (IOException e) {
			throw new LeaveServiceUnreachableException("Unable to reach leave-request-service", e);
		} catch (RuntimeException e) {
			if (e.getCause() instanceof LeaveServiceUnreachableException) {
				throw (LeaveServiceUnreachableException) e.getCause();
			}
			throw e; // rethrow if it's another runtime exception
		}
	}
}
