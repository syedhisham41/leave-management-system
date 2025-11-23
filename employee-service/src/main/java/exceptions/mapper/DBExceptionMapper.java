package exceptions.mapper;

import constants.enums.Error_code;
import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.DTO_api_response;
import exceptions.exception.DataAccessException;
import exceptions.exception.DbNotFoundException;
import exceptions.exception.DepartmentManagerTableEmptyException;
import exceptions.exception.DepartmentNotFoundException;
import exceptions.exception.DepartmentTableEmptyException;

public class DBExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof DataAccessException || e instanceof DbNotFoundException
				|| e instanceof DepartmentTableEmptyException || e instanceof DepartmentManagerTableEmptyException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();
		if (e instanceof DbNotFoundException) {
			response.setCode(Error_code.error_code.DB_CONNECTION_FAILED.toString());
			response.setHttp_code(error_code.DB_CONNECTION_FAILED.getHttp_code());
			response.setMessage(error_code.DB_CONNECTION_FAILED.getMessage());
		}

		else if (e instanceof DataAccessException) {
			response.setCode(Error_code.error_code.INTERNAL_ERROR.toString());
			response.setHttp_code(error_code.INTERNAL_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof DepartmentNotFoundException) {
			response.setCode(Error_code.error_code.DEPARTMENT_NOT_FOUND.toString());
			response.setHttp_code(error_code.DEPARTMENT_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof DepartmentTableEmptyException) {
			response.setCode(success_code.DEPARTMENT_LIST_EMPTY.toString());
			response.setHttp_code(success_code.DEPARTMENT_LIST_EMPTY.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof DepartmentManagerTableEmptyException) {
			response.setCode(success_code.MANAGER_LIST_EMPTY.toString());
			response.setHttp_code(error_code.MANAGER_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
