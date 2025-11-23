package handler;

import java.io.IOException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import dto.DTO_department;
import dto.DTO_department_query_params;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeTableEmptyException;
import exceptions.exception.UnIdentifiedParameterException;
import service.Service_employee_get;
import utils.handler.common.Utils_handler;
import utils.http.Utils_http;

public class Handler_get_all_departments_with_query implements HttpHandler {

	private final Service_employee_get emp_service;

	public Handler_get_all_departments_with_query(Service_employee_get emp_service) {
		this.emp_service = emp_service;
	}

	public void handle(HttpExchange exchange) throws IOException {

		String method = exchange.getRequestMethod();

		if (!method.equalsIgnoreCase("GET")) {
			exchange.sendResponseHeaders(405, -1);
			return;
		}

		DTO_department_query_params qp = null;
		String query = exchange.getRequestURI().getRawQuery();
		System.out.println("Raw query string: " + exchange.getRequestURI().getRawQuery());
		boolean urlWithQuery = query != null && !query.isEmpty();

		if (urlWithQuery) {
			try {
				qp = Utils_handler.parseDepartmentQueryParams(query);
			} catch (UnIdentifiedParameterException e) {
				e.printStackTrace();
			}
		}

		List<DTO_department> dep_list = null;
		try {
			if (urlWithQuery)
				try {
					dep_list = this.emp_service.getDepartmentsByQuery(qp);
				} catch (UnIdentifiedParameterException e) {
					e.printStackTrace();
					exchange.sendResponseHeaders(400, -1);
				}
			else
				dep_list = this.emp_service.getAllDepartments();
		} catch (DataAccessException e) {
			System.err.println("Error in data base while fetching departments: " + e.getMessage());
			e.printStackTrace();
			exchange.sendResponseHeaders(500, -1);
			return;
		} catch (EmployeeTableEmptyException e) {
			System.err.println("table is empty " + e.getMessage());
			e.printStackTrace();
			exchange.sendResponseHeaders(204, -1);
			return;
		}

		if (dep_list != null && !dep_list.isEmpty()) {
			Utils_http.sendJsonResponse(exchange, dep_list, 200);
		} else {
			exchange.sendResponseHeaders(204, -1);
			return;
		}
	}
}
