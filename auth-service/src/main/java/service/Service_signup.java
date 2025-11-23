package service;

import java.sql.SQLException;

import dao.interfaces.DAO_Signup;
import dto.DTO_user_signup;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.InsufficientPasswordLengthException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidSaltForHashFunctionGenerationException;
import exceptions.exception.PasswordHashKeyGenerationException;
import exceptions.exception.UserRecordDeleteFailedException;
import exceptions.exception.UserRecordNotFoundException;
import model.User;
import utils.service.Utils_common;
import utils.service.Utils_service_signup_validator;
import utils.service.proxy.Utils_employee_service_proxy;

public class Service_signup {

	private final DAO_Signup signup_dao;

	public Service_signup(DAO_Signup signup_dao) {
		this.signup_dao = signup_dao;
	}

	public int signup(DTO_user_signup user_details) throws EmployeeNotFoundException, DataAccessException,
			PasswordHashKeyGenerationException, InvalidSaltForHashFunctionGenerationException,
			InsufficientPasswordLengthException, InvalidEmployeeEmailException {

		// validate password
		Utils_service_signup_validator.validatePassword(user_details.getPassword());

		// validate employee_id and check if its valid one in employee db
		Utils_employee_service_proxy.isEmployeeValid(user_details.getEmp_id());

		// validate email_id and check if its a valid one in employee db
		Utils_service_signup_validator.validateEmailId(user_details.getEmail());
		Utils_employee_service_proxy.isEmailValid(user_details.getEmail());

		// generate salt, password_hash
		String salt = Utils_common.generateSalt();
		String password_hash = Utils_common.hashWithPBKDF2(user_details.getPassword(), salt);

		try {
			return this.signup_dao
					.signup(new User(user_details.getEmp_id(), user_details.getEmail(), password_hash, salt));
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}

	}

	public void deleteUserRecord(int employee_id)
			throws UserRecordNotFoundException, DataAccessException, UserRecordDeleteFailedException {

		try {
			// validate is employee is present in the user db
			this.signup_dao.getUserRecord(employee_id);

			this.signup_dao.deleteUserRecord(employee_id);

		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}
}
