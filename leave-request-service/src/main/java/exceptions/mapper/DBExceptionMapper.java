package exceptions.mapper;

import constants.enums.Error_code;
import dto.DTO_api_response;
import exceptions.exception.DataAccessException;
import exceptions.exception.DbNotFoundException;
import exceptions.exception.LeaveBalanceUpdateFailedException;
import exceptions.exception.LeaveRequestApprovalFailedException;

public class DBExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof DataAccessException || e instanceof DbNotFoundException
				|| e instanceof LeaveRequestApprovalFailedException || e instanceof LeaveBalanceUpdateFailedException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();
		if (e instanceof DbNotFoundException) {
			response.setCode(Error_code.DB_CONNECTION_FAILED.toString());
			response.setHttp_code(Error_code.DB_CONNECTION_FAILED.getHttp_code());
			response.setMessage(Error_code.DB_CONNECTION_FAILED.getMessage());
		}

		else if (e instanceof DataAccessException) {
			response.setCode(Error_code.INTERNAL_ERROR.toString());
			response.setHttp_code(Error_code.INTERNAL_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveRequestApprovalFailedException) {
			response.setCode(Error_code.LEAVE_REQUEST_APPROVAL_FAILED.toString());
			response.setHttp_code(Error_code.LEAVE_REQUEST_APPROVAL_FAILED.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveBalanceUpdateFailedException) {
			response.setCode(Error_code.LEAVE_BALANCE_UPDATE_FAILED.toString());
			response.setHttp_code(Error_code.LEAVE_BALANCE_UPDATE_FAILED.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
