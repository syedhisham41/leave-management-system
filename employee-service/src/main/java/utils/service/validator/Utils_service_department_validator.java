package utils.service.validator;

import java.util.Arrays;

import constants.enums.Department;
import exceptions.exception.InvalidDepartmentIdException;
import exceptions.exception.UnknownDepartmentIdException;

public class Utils_service_department_validator {

	public static void validateDepartmentId(String department_id)
			throws InvalidDepartmentIdException, UnknownDepartmentIdException {

		// null check
		if (department_id == null || department_id.isBlank())
			throw new InvalidDepartmentIdException("department ID is null or invalid", null);

		// validity check
		boolean isValid = Arrays.stream(Department.values()).anyMatch(d -> d.name().equals(department_id));
		if (!isValid)
			throw new UnknownDepartmentIdException("Unknown department ID", null);

	}

}
