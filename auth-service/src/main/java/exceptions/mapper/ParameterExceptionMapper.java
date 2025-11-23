package exceptions.mapper;

import constants.enums.Error_code;
import dto.DTO_api_response;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidEmployeePasswordException;
import exceptions.exception.InvalidSaltForHashFunctionGenerationException;
import exceptions.exception.PasswordHashKeyGenerationException;

public class ParameterExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof EmployeeNotFoundException || e instanceof InvalidSaltForHashFunctionGenerationException
				|| e instanceof PasswordHashKeyGenerationException || e instanceof EmployeeIdMissingException
				|| e instanceof InvalidEmployeePasswordException || e instanceof InvalidEmployeeEmailException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();

		if (e instanceof EmployeeNotFoundException) {
			response.setCode(Error_code.EMPLOYEE_NOT_FOUND.toString());
			response.setHttp_code(Error_code.EMPLOYEE_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof InvalidSaltForHashFunctionGenerationException) {
			response.setCode(Error_code.INVALID_SALT_KEY.toString());
			response.setHttp_code(Error_code.INVALID_SALT_KEY.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof PasswordHashKeyGenerationException) {
			response.setCode(Error_code.HASH_FUNCTION_ERROR.toString());
			response.setHttp_code(Error_code.HASH_FUNCTION_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof EmployeeIdMissingException) {
			response.setCode(Error_code.EMPLOYEE_ID_MISSING.toString());
			response.setHttp_code(Error_code.EMPLOYEE_ID_MISSING.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof InvalidEmployeeEmailException) {
			response.setCode(Error_code.INVALID_EMAIL.toString());
			response.setHttp_code(Error_code.INVALID_EMAIL.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof InvalidEmployeePasswordException) {
			response.setCode(Error_code.INVALID_PASSWORD.toString());
			response.setHttp_code(Error_code.INVALID_PASSWORD.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
