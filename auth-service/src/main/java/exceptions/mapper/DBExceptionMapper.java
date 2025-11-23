package exceptions.mapper;

import constants.enums.Error_code;
import dto.DTO_api_response;
import exceptions.exception.DataAccessException;
import exceptions.exception.LastLoginUpdateFailedException;
import exceptions.exception.UserNotFoundException;
import exceptions.exception.UserRecordDeleteFailedException;
import exceptions.exception.UserRecordNotFoundException;

public class DBExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof DataAccessException || e instanceof UserNotFoundException
				|| e instanceof LastLoginUpdateFailedException || e instanceof UserRecordDeleteFailedException
				|| e instanceof UserRecordNotFoundException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();

		if (e instanceof DataAccessException) {
			response.setCode(Error_code.INTERNAL_ERROR.toString());
			response.setHttp_code(Error_code.INTERNAL_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof UserNotFoundException) {
			response.setCode(Error_code.USER_NOT_FOUND.toString());
			response.setHttp_code(Error_code.USER_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof LastLoginUpdateFailedException) {
			response.setCode(Error_code.LAST_LOGIN_UPDATE_FAILED.toString());
			response.setHttp_code(Error_code.LAST_LOGIN_UPDATE_FAILED.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof UserRecordNotFoundException) {
			response.setCode(Error_code.USER_NOT_FOUND.toString());
			response.setHttp_code(Error_code.USER_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof UserRecordDeleteFailedException) {
			response.setCode(Error_code.USER_RECORD_DELETE_FAILED.toString());
			response.setHttp_code(Error_code.USER_RECORD_DELETE_FAILED.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
