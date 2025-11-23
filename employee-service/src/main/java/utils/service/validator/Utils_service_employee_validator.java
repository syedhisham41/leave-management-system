package utils.service.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import dto.DTO_employee_query_params;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidEmployeeJoiningDateException;
import exceptions.exception.InvalidEmployeeNameException;
import exceptions.exception.InvalidOrderException;
import exceptions.exception.InvalidSortByException;

public class Utils_service_employee_validator {

	public static void employeeNameValidate(String employeeName) throws InvalidEmployeeNameException {
		if (employeeName == null || employeeName.trim().isEmpty())
			throw new InvalidEmployeeNameException("Missing or blank value for employee name parameter", null);

		if (!employeeName.matches("^[A-Za-z\\s'-]{2,50}$")) {
			throw new InvalidEmployeeNameException("invalid employee name format: " + employeeName, null);
		}
	}

	public static void employeeEmailValidate(String employeeEmail) throws InvalidEmployeeEmailException {
		if (employeeEmail == null || employeeEmail.trim().isEmpty())
			throw new InvalidEmployeeEmailException("Missing or blank value for employee email parameter", null);

		if (!employeeEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			throw new InvalidEmployeeEmailException("invalid employee email format: " + employeeEmail, null);
		}
	}

	public static void employeeJoiningDateValidate(String joining_date) throws InvalidEmployeeJoiningDateException {

		if (joining_date == null || joining_date.trim().isEmpty())
			throw new InvalidEmployeeJoiningDateException("Missing or blank value for employee joining date parameter",
					null);
		DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
		date.setLenient(false);
		try {
			date.parse(joining_date);
		} catch (ParseException e) {
			throw new InvalidEmployeeJoiningDateException("invalid employee joining date value: " + joining_date, null);
		}
	}

	public static void validateSortByAndOrder(DTO_employee_query_params query_params)
			throws InvalidOrderException, InvalidSortByException {

		Set<String> orderSet = Set.of("asc", "desc");
		Set<String> sortBySet = Set.of("emp_name", "emp_id", "emp_department_id");

		if (query_params.getOrder() != null && !orderSet.contains(query_params.getOrder().toLowerCase()))
			throw new InvalidOrderException("'order' query parameter is invalid", null);

		if (query_params.getSortBy() != null && !sortBySet.contains(query_params.getSortBy().toLowerCase()))
			throw new InvalidSortByException("'sortby' query parameter is invalid", null);

	}

}