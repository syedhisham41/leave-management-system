package exceptions.mapper;

import constants.enums.Error_code.error_code;
import dto.DTO_api_response;
import exceptions.exception.UnauthorizedException;
import exceptions.exception.UserAccessDeniedException;

public class AuthExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof UnauthorizedException || e instanceof UserAccessDeniedException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();

		if (e instanceof UnauthorizedException) {
			response.setCode(error_code.UNAUTHORIZED_ERROR.toString());
			response.setHttp_code(error_code.UNAUTHORIZED_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof UserAccessDeniedException) {
			response.setCode(error_code.FORBIDDEN_ERROR.toString());
			response.setHttp_code(error_code.FORBIDDEN_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
