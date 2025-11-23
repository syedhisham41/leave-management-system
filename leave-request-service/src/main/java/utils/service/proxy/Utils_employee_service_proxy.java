package utils.service.proxy;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.PropertyConfig;
import dto.DTO_api_response;
import dto.DTO_employee;
import dto.DTO_manager_check;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.ManagerNotFoundException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
					throw new EmployeeNotFoundException("Employee not found in employee-service");

				default:
					throw new DataAccessException("Unexpected error from employee-service: HTTP " + statusCode, null);
			}

		} catch (IOException e) {
			throw new DataAccessException("Unable to reach employee-service", e);
		}
	}

	public static boolean isManagerValid(int manager_id)
			throws EmployeeNotFoundException, DataAccessException, ManagerNotFoundException {

		try {
			isEmployeeValid(manager_id);
		} catch (EmployeeNotFoundException e) {
			throw new ManagerNotFoundException("the given manager is not an employee", null);
		}

		String url = EMPLOYEE_SERVICE_URL + "manager/check?id=" + manager_id;

		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
				.setResponseTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).build();

		try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build()) {
			HttpGet request = new HttpGet(URI.create(url));

			request.addHeader("X-Service-Auth", INTERNAL_TOKEN);

			ClassicHttpResponse response = client.executeOpen(null, request, null);

			int statusCode = response.getCode();

			ObjectMapper om = new ObjectMapper();
			switch (statusCode) {

				case 200:
					JavaType type = om.getTypeFactory().constructType(DTO_manager_check.class);
					JavaType wrapperType = om.getTypeFactory().constructParametricType(DTO_api_response.class, type);

					DTO_api_response<DTO_manager_check> apiResponse = om.readValue(response.getEntity().getContent(),
							wrapperType);

					return apiResponse.getData().getIsManager();

				case 404:
					throw new ManagerNotFoundException("Manager not found in employee-service", null);

				default:
					throw new DataAccessException("Unexpected error from employee-service: HTTP " + statusCode, null);
			}

		} catch (IOException e) {
			throw new DataAccessException("Unable to reach employee-service", e);
		}
	}

	public static List<Integer> getIdOfEmployeesByManagerId(int manager_id)
			throws EmployeeNotFoundException, DataAccessException {

		String url = EMPLOYEE_SERVICE_URL + "employee/get?manager=" + manager_id;

		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
				.setResponseTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).build();

		try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build()) {
			HttpGet request = new HttpGet(URI.create(url));

			request.addHeader("X-Service-Auth", INTERNAL_TOKEN);

			ClassicHttpResponse response = client.executeOpen(null, request, null);

			int statusCode = response.getCode();

			ObjectMapper om = new ObjectMapper();
			switch (statusCode) {

				case 200:

					JavaType type = om.getTypeFactory().constructParametricType(List.class, DTO_employee.class);
					JavaType wrapperType = om.getTypeFactory().constructParametricType(DTO_api_response.class, type);

					DTO_api_response<List<DTO_employee>> apiResponse = om.readValue(response.getEntity().getContent(),
							wrapperType);

					List<DTO_employee> employeeList = apiResponse.getData();
					List<Integer> ids = new ArrayList<>();

					for (DTO_employee emp : employeeList) {
						ids.add(emp.getId());
					}
					return ids;

				case 404:
					throw new EmployeeNotFoundException("Employee not found in employee-service");

				default:
					throw new DataAccessException("Unexpected error from employee-service: HTTP " + statusCode, null);
			}

		} catch (IOException e) {
			throw new DataAccessException("Unable to reach employee-service", e);
		}
	}
}
