package utils.handler.validator;

import java.util.Map;

import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidLimitParameterException;
import exceptions.exception.InvalidOrderException;
import exceptions.exception.InvalidPageParameterException;
import exceptions.exception.InvalidSortByException;
import exceptions.exception.UnIdentifiedParameterException;

public class Utils_handler_employee_validator {

	public static String[] supportedDepartmentIdKeys = { "department_id", "id", "department", "departmentid" };

	public static String[] supportedEmployeeIdKeys = { "employee_id", "id", "employeeid" };

	public static String[] supportedManagerIdKeys = { "manager_id", "managerid", "manager" };

	public static String[] supportedSortByKeys = { "sortBy", "sort", "sortby" };

	public static String[] supportedOrderByKeys = { "orderBy", "order" };

	public static String[] supportedEmailKeys = { "email_id", "email", "mail" };

	public static String[] supportedPageKeys = { "page" };

	public static String[] supportedLimitKeys = { "limit" };

	public static String getEmployeeIdFromQueryAfterValidation(Map<String, String> params)
			throws UnIdentifiedParameterException, EmployeeIdMissingException {

		for (String key : supportedEmployeeIdKeys) {
			if (params.containsKey(key)) {
				String employee_id = params.get(key);
				if (employee_id != null && !employee_id.isBlank())
					return employee_id;
				throw new EmployeeIdMissingException(
						"Missing or blank value for required query parameter: 'employee_id' or 'id'", null);
			}
		}
		throw new UnIdentifiedParameterException("Unrecognized query parameter. Expected 'employee_id' or 'id'", null);
	}

	public static String getSortByValueFromQueryAfterValidation(Map<String, String> params)
			throws UnIdentifiedParameterException, InvalidSortByException {

		for (String key : supportedSortByKeys) {
			if (params.containsKey(key)) {
				String sortBy = params.get(key);
				if (sortBy != null && !sortBy.isBlank())
					return sortBy;
				throw new InvalidSortByException(
						"Missing or blank value for required query parameter: 'sortBy' or 'sort'", null);
			}
		}
		throw new UnIdentifiedParameterException("Unrecognized query parameter. Expected 'sortBy' or 'sort'", null);
	}

	public static String getOrderByValueFromQueryAfterValidation(Map<String, String> params)
			throws UnIdentifiedParameterException, InvalidOrderException {

		for (String key : supportedOrderByKeys) {
			if (params.containsKey(key)) {
				String orderBy = params.get(key).toUpperCase();
				if (orderBy != null && !orderBy.isBlank())
					return orderBy;
				throw new InvalidOrderException(
						"Missing or blank value for required query parameter: 'orderBy' or 'order'", null);
			}
		}
		throw new UnIdentifiedParameterException("Unrecognized query parameter. Expected 'orderBy' or 'order'", null);
	}

	public static String getPageValueFromQueryAfterValidation(Map<String, String> params)
			throws UnIdentifiedParameterException, InvalidPageParameterException {

		for (String key : supportedPageKeys) {
			if (params.containsKey(key)) {
				String page = params.get(key);
				if (page != null && !page.isBlank())
					return page;
				throw new InvalidPageParameterException("Missing or blank value for required query parameter: 'page'",
						null);
			}
		}
		throw new UnIdentifiedParameterException("Unrecognized query parameter. Expected 'page'", null);
	}

	public static String getLimitValueFromQueryAfterValidation(Map<String, String> params)
			throws UnIdentifiedParameterException, InvalidLimitParameterException {

		for (String key : supportedLimitKeys) {
			if (params.containsKey(key)) {
				String limit = params.get(key);
				if (limit != null && !limit.isBlank())
					return limit;
				throw new InvalidLimitParameterException(
						"Missing or blank value for required query parameter: 'limit' ", null);
			}
		}
		throw new UnIdentifiedParameterException("Unrecognized query parameter. Expected 'limit' ", null);
	}

	public static String getEmailIdKeyFromQueryAfterValidation(Map<String, String> params)
			throws UnIdentifiedParameterException, InvalidEmployeeEmailException {

		for (String key : supportedEmailKeys) {
			if (params.containsKey(key)) {
				String email = params.get(key);
				if (email != null && !email.isBlank())
					return email;
				throw new InvalidEmployeeEmailException(
						"Missing or blank value for required query parameter: 'email' or 'email_id' or 'mail'", null);
			}
		}
		throw new UnIdentifiedParameterException(
				"Unrecognized query parameter. Expected 'email' or 'email_id' or 'mail'", null);
	}
}
