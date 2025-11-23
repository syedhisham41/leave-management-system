package service;

import java.sql.SQLException;

import dao.interfaces.DAO_Login;
import dto.DTO_user_login_request;
import dto.DTO_user_login_response;
import model.Jwt;
import model.User;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.InsufficientPasswordLengthException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidEmployeePasswordException;
import exceptions.exception.InvalidSaltForHashFunctionGenerationException;
import exceptions.exception.LastLoginUpdateFailedException;
import exceptions.exception.PasswordHashKeyGenerationException;
import exceptions.exception.UserNotFoundException;
import utils.service.Utils_common;
import utils.service.Utils_service_signup_validator;
import utils.service.proxy.Utils_employee_service_proxy;

public class Service_login {

	private final DAO_Login login_dao;

	public Service_login(DAO_Login login_dao) {
		this.login_dao = login_dao;
	}

	public DTO_user_login_response login(DTO_user_login_request user_details)
			throws InsufficientPasswordLengthException, InvalidEmployeeEmailException, EmployeeNotFoundException,
			DataAccessException, PasswordHashKeyGenerationException, InvalidSaltForHashFunctionGenerationException,
			InvalidEmployeePasswordException, UserNotFoundException, LastLoginUpdateFailedException {

		// validate password
		Utils_service_signup_validator.validatePassword(user_details.getPassword());

		// validate email_id and check if its a valid one in employee db
		Utils_service_signup_validator.validateEmailId(user_details.getEmail());
		Utils_employee_service_proxy.isEmailValid(user_details.getEmail());

		try {
			User user_record = this.login_dao.login(user_details);

			// verify if the password hash is same
			String password_hash = Utils_common.hashWithPBKDF2(user_details.getPassword(), user_record.getSalt());
			if (!password_hash.equals(user_record.getPassword_hash()))
				throw new InvalidEmployeePasswordException(
						"Password is invalid for the employee with emailId : " + user_details.getEmail(), null);

			// generate jwt token
			Jwt jwt = Utils_common.generateJwtToken(user_record.getEmp_id(), user_details.getEmail(),
					user_record.getRole());

			// update last_login_at
			this.login_dao.updateLastLogin(user_record.getEmp_id());

			return new DTO_user_login_response(user_record.getEmp_id(), user_details.getEmail(), user_record.getRole(),
					jwt.getToken(), jwt.getIssued_at(), jwt.getExpired_at());

		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}
}
