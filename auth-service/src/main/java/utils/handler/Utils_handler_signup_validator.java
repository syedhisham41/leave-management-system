package utils.handler;

import dto.DTO_user_signup;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidEmployeePasswordException;

public class Utils_handler_signup_validator {

	public static void validateSignupParameter(DTO_user_signup user)
			throws EmployeeIdMissingException, InvalidEmployeeEmailException, InvalidEmployeePasswordException {

		if (user.getEmp_id() <= 0)
			throw new EmployeeIdMissingException("employee_id is invalid", null);

		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new InvalidEmployeeEmailException("employee_email is empty or null", null);
		}

		if (user.getPassword() == null || user.getPassword().isBlank() || user.getPassword().isEmpty()) {
			throw new InvalidEmployeePasswordException("employee password provided is empty or null", null);
		}

	}

}
