package exceptions.mapper;

import java.security.InvalidParameterException;

import constants.enums.Error_code;
import constants.enums.Error_code.error_code;
import dto.DTO_api_response;
import exceptions.exception.DepartmentIdMissingException;
import exceptions.exception.InvalidDepartmentIdException;
import exceptions.exception.InvalidDepartmentParameterException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidEmployeeJoiningDateException;
import exceptions.exception.InvalidEmployeeNameException;
import exceptions.exception.InvalidLimitParameterException;
import exceptions.exception.InvalidOrderException;
import exceptions.exception.InvalidPageParameterException;
import exceptions.exception.InvalidSortByException;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.UnIdentifiedParameterException;

public class ParameterExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof InvalidSortByException || e instanceof InvalidOrderException
				|| e instanceof UnIdentifiedParameterException || e instanceof EmployeeIdMissingException
				|| e instanceof InvalidDepartmentParameterException || e instanceof NumberFormatException
				|| e instanceof InvalidLimitParameterException || e instanceof InvalidPageParameterException
				|| e instanceof InvalidEmployeeNameException || e instanceof InvalidEmployeeEmailException
				|| e instanceof InvalidEmployeeJoiningDateException || e instanceof InvalidDepartmentIdException
				|| e instanceof DepartmentIdMissingException || e instanceof InvalidParameterException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();

		if (e instanceof InvalidOrderException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_ORDER.toString());
			response.setHttp_code(error_code.UNSUPPORTED_ORDER.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidSortByException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_SORT_FIELD.toString());
			response.setHttp_code(error_code.UNSUPPORTED_SORT_FIELD.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof UnIdentifiedParameterException) {
			response.setCode(Error_code.error_code.UNKNOWN_PARAMETER.toString());
			response.setHttp_code(error_code.UNKNOWN_PARAMETER.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof EmployeeIdMissingException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_ID.toString());
			response.setHttp_code(error_code.UNSUPPORTED_ID.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidPageParameterException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_PAGE.toString());
			response.setHttp_code(error_code.UNSUPPORTED_PAGE.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidLimitParameterException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_LIMIT.toString());
			response.setHttp_code(error_code.UNSUPPORTED_LIMIT.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidEmployeeNameException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_NAME.toString());
			response.setHttp_code(error_code.UNSUPPORTED_NAME.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidEmployeeEmailException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_EMAIL.toString());
			response.setHttp_code(error_code.UNSUPPORTED_EMAIL.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidEmployeeJoiningDateException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_JOINING_DATE.toString());
			response.setHttp_code(error_code.UNSUPPORTED_JOINING_DATE.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidDepartmentParameterException) {
			response.setCode(Error_code.error_code.UNSUPPORTED_DEPARTMENT.toString());
			response.setHttp_code(error_code.UNSUPPORTED_DEPARTMENT.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof NumberFormatException) {
			response.setCode(Error_code.error_code.INVALID_NUMBER_FORMAT.toString());
			response.setHttp_code(error_code.INVALID_NUMBER_FORMAT.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidDepartmentIdException) {
			response.setCode(Error_code.error_code.UNKNOWN_DEPARTMENT.toString());
			response.setHttp_code(error_code.UNKNOWN_DEPARTMENT.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof DepartmentIdMissingException) {
			response.setCode(Error_code.error_code.DEPARTMENT_ID_MISSING.toString());
			response.setHttp_code(error_code.DEPARTMENT_ID_MISSING.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidParameterException) {
			response.setCode(Error_code.error_code.INVALID_PARAMETER.toString());
			response.setHttp_code(error_code.INVALID_PARAMETER.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
