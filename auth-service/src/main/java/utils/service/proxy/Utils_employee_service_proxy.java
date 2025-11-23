package utils.service.proxy;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;

import config.PropertyConfig;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;

public class Utils_employee_service_proxy {

	private static final int TIMEOUT_MILLIS = 5000;
	private static final String INTERNAL_TOKEN = PropertyConfig.get("SERVICE_SECRET");
	private static final String EMPLOYEE_SERVICE_URL = PropertyConfig.get("EMPLOYEE_SERVICE_URL");

	public static boolean isEmployeeValid(int employeeId) throws EmployeeNotFoundException, DataAccessException {

		String url = EMPLOYEE_SERVICE_URL + "employee/get?id=" + employeeId;

		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
				.setResponseTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).build();

		try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build()) {
			HttpGet request = new HttpGet(URI.create(url));

			request.addHeader("X-Service-Auth", INTERNAL_TOKEN);

			ClassicHttpResponse response = client.executeOpen(null, request, null);

			int statusCode = response.getCode();

			switch (statusCode) {

				case 200:
					return true;

				case 404:
					throw new EmployeeNotFoundException(
							"Employee not found with the given employee_id in employee-service : " + employeeId);

				default:
					throw new DataAccessException("Unexpected error from employee-service: HTTP " + statusCode, null);
			}

		} catch (IOException e) {
			throw new DataAccessException("Unable to reach employee-service", e);
		}
	}

	public static boolean isEmailValid(String email) throws EmployeeNotFoundException, DataAccessException {
		String url = EMPLOYEE_SERVICE_URL + "employee/get?email=" + email;

		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
				.setResponseTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).build();

		try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build()) {
			HttpGet request = new HttpGet(URI.create(url));

			request.addHeader("X-Service-Auth", INTERNAL_TOKEN);

			ClassicHttpResponse response = client.executeOpen(null, request, null);

			int statusCode = response.getCode();

			switch (statusCode) {

				case 200:
					return true;

				case 404:
					throw new EmployeeNotFoundException(
							"Employee not found with the provided email_id in employee-service : " + email);

				default:
					throw new DataAccessException("Unexpected error from employee-service: HTTP " + statusCode, null);
			}

		} catch (IOException e) {
			throw new DataAccessException("Unable to reach employee-service", e);
		}

	}
}
