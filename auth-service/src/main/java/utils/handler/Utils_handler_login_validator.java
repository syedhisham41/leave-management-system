package utils.handler;

import dto.DTO_user_login_request;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidEmployeePasswordException;

public class Utils_handler_login_validator {

	public static void validateLoginParameter(DTO_user_login_request request)
			throws InvalidEmployeeEmailException, InvalidEmployeePasswordException {

		if (request.getEmail() == null || request.getEmail().isEmpty()) {
			throw new InvalidEmployeeEmailException("employee_email is empty or null", null);
		}

		if (request.getPassword() == null || request.getPassword().isBlank() || request.getPassword().isEmpty()) {
			throw new InvalidEmployeePasswordException("employee password provided is empty or null", null);
		}

	}

}
