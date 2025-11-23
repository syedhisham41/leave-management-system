package utils.service;

import exceptions.exception.InsufficientPasswordLengthException;
import exceptions.exception.InvalidEmployeeEmailException;

public class Utils_service_signup_validator {

	public static void validatePassword(String password) throws InsufficientPasswordLengthException {

		// password should be at least 6 characters
		if (password.length() < 6) {
			throw new InsufficientPasswordLengthException("Password should be atleast 6 characters", null);
		}

		// add additional validations like mix of characters etc TODO

	}

	public static void validateEmailId(String email) throws InvalidEmployeeEmailException {

		if (email == null || email.trim().isEmpty())
			throw new InvalidEmployeeEmailException("Missing or blank value for employee email parameter", null);

		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			throw new InvalidEmployeeEmailException("invalid employee email format: " + email, null);
		}
	}
}
