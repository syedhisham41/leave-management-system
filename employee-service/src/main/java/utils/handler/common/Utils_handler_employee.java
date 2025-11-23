package utils.handler.common;

import java.util.Arrays;
import java.util.Map;

import dto.DTO_employee_query_params;
import exceptions.exception.DepartmentIdMissingException;
import exceptions.exception.InvalidLimitParameterException;
import exceptions.exception.InvalidOrderException;
import exceptions.exception.InvalidPageParameterException;
import exceptions.exception.InvalidSortByException;
import exceptions.exception.UnIdentifiedParameterException;
import utils.handler.validator.Utils_handler_department_validator;
import utils.handler.validator.Utils_handler_employee_validator;

public class Utils_handler_employee {

	public static DTO_employee_query_params generateEmployeeDtoFromQueryMap(Map<String, String> params)
			throws DepartmentIdMissingException, UnIdentifiedParameterException, InvalidSortByException,
			InvalidOrderException, InvalidPageParameterException, InvalidLimitParameterException {

		DTO_employee_query_params employee_dto = new DTO_employee_query_params();

		boolean ifEmployeeWithDepartmentId = Arrays.stream(Utils_handler_employee_validator.supportedDepartmentIdKeys)
				.anyMatch(params::containsKey);

		boolean ifEmployeeWithSortByKey = Arrays.stream(Utils_handler_employee_validator.supportedSortByKeys)
				.anyMatch(params::containsKey);

		boolean ifEmployeeWithOrderKey = Arrays.stream(Utils_handler_employee_validator.supportedOrderByKeys)
				.anyMatch(params::containsKey);

		boolean ifEmployeeWithPageKey = Arrays.stream(Utils_handler_employee_validator.supportedPageKeys)
				.anyMatch(params::containsKey);

		boolean ifEmployeeWithLimitKey = Arrays.stream(Utils_handler_employee_validator.supportedLimitKeys)
				.anyMatch(params::containsKey);

		if (ifEmployeeWithDepartmentId) {
			String department_id = Utils_handler_department_validator.getDepartmentIdFromQueryAfterValidation(params);
			employee_dto.setDepartmentId(department_id);
		}

		if (ifEmployeeWithSortByKey) {
			String sortBy = Utils_handler_employee_validator.getSortByValueFromQueryAfterValidation(params);
			employee_dto.setSortBy(sortBy);
		}

		if (ifEmployeeWithOrderKey) {
			String orderBy = Utils_handler_employee_validator.getOrderByValueFromQueryAfterValidation(params);
			employee_dto.setOrder(orderBy);
		}
		if (ifEmployeeWithPageKey) {
			try {
				String page = Utils_handler_employee_validator.getPageValueFromQueryAfterValidation(params);
				employee_dto.setPage(Integer.parseInt(page));
			} catch (NumberFormatException e) {
				throw new InvalidPageParameterException("Invalid page value: must be a number", e);
			}
		}
		if (ifEmployeeWithLimitKey) {
			try {
				String limit = Utils_handler_employee_validator.getLimitValueFromQueryAfterValidation(params);
				employee_dto.setLimit(Integer.parseInt(limit));
			} catch (NumberFormatException e) {
				throw new InvalidLimitParameterException("Invalid limit value: must be a number", e);
			}
		}

			if (employee_dto.getDepartmentId() == null && employee_dto.getSortBy() == null
					&& employee_dto.getOrder() == null && employee_dto.getPage() == 0 && employee_dto.getLimit() == 0) {
				throw new UnIdentifiedParameterException("No valid query parameters provided", null);
			}

		return employee_dto;
	}
	
	public static DTO_employee_query_params generateEmployeeDtoFromQueryMapForManagerFilter(Map<String, String> params)
			throws DepartmentIdMissingException, UnIdentifiedParameterException, InvalidSortByException,
			InvalidOrderException, InvalidPageParameterException, InvalidLimitParameterException {

		DTO_employee_query_params employee_dto = new DTO_employee_query_params();

		boolean ifEmployeeWithDepartmentId = Arrays.stream(Utils_handler_employee_validator.supportedDepartmentIdKeys)
				.anyMatch(params::containsKey);

		boolean ifEmployeeWithSortByKey = Arrays.stream(Utils_handler_employee_validator.supportedSortByKeys)
				.anyMatch(params::containsKey);

		boolean ifEmployeeWithOrderKey = Arrays.stream(Utils_handler_employee_validator.supportedOrderByKeys)
				.anyMatch(params::containsKey);

		boolean ifEmployeeWithPageKey = Arrays.stream(Utils_handler_employee_validator.supportedPageKeys)
				.anyMatch(params::containsKey);

		boolean ifEmployeeWithLimitKey = Arrays.stream(Utils_handler_employee_validator.supportedLimitKeys)
				.anyMatch(params::containsKey);

		if (ifEmployeeWithDepartmentId) {
			String department_id = Utils_handler_department_validator.getDepartmentIdFromQueryAfterValidation(params);
			employee_dto.setDepartmentId(department_id);
		}

		if (ifEmployeeWithSortByKey) {
			String sortBy = Utils_handler_employee_validator.getSortByValueFromQueryAfterValidation(params);
			employee_dto.setSortBy(sortBy);
		}

		if (ifEmployeeWithOrderKey) {
			String orderBy = Utils_handler_employee_validator.getOrderByValueFromQueryAfterValidation(params);
			employee_dto.setOrder(orderBy);
		}
		if (ifEmployeeWithPageKey) {
			try {
				String page = Utils_handler_employee_validator.getPageValueFromQueryAfterValidation(params);
				employee_dto.setPage(Integer.parseInt(page));
			} catch (NumberFormatException e) {
				throw new InvalidPageParameterException("Invalid page value: must be a number", e);
			}
		}
		if (ifEmployeeWithLimitKey) {
			try {
				String limit = Utils_handler_employee_validator.getLimitValueFromQueryAfterValidation(params);
				employee_dto.setLimit(Integer.parseInt(limit));
			} catch (NumberFormatException e) {
				throw new InvalidLimitParameterException("Invalid limit value: must be a number", e);
			}
		}

		return employee_dto;
	}
}
