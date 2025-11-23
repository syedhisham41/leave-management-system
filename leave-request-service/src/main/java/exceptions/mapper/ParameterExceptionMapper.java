package exceptions.mapper;

import constants.enums.Error_code;
import dto.DTO_api_response;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.EmptyBodyForLeaveApprovalActionException;
import exceptions.exception.InvalidApproverIdException;
import exceptions.exception.InvalidAuditActionTypeException;
import exceptions.exception.InvalidAuditLeaveDaysException;
import exceptions.exception.InvalidAuditManagerFlagException;
import exceptions.exception.InvalidDateException;
import exceptions.exception.InvalidEmployeeIdException;
import exceptions.exception.InvalidHalfDayTypeException;
import exceptions.exception.InvalidLeaveActionTypeException;
import exceptions.exception.InvalidLeaveIdException;
import exceptions.exception.InvalidLeaveTypeException;
import exceptions.exception.LeaveAuditCommentsNotFoundException;
import exceptions.exception.LeaveIdMissingException;
import exceptions.exception.LeaveReasonEmptyException;
import exceptions.exception.ManagerIdMissingException;
import exceptions.exception.UnidentifiedParameterException;
import exceptions.exception.UnsupportedQueryParameterException;

public class ParameterExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof InvalidEmployeeIdException || e instanceof InvalidDateException
				|| e instanceof UnidentifiedParameterException || e instanceof InvalidHalfDayTypeException
				|| e instanceof InvalidLeaveTypeException || e instanceof NumberFormatException
				|| e instanceof LeaveReasonEmptyException || e instanceof LeaveIdMissingException
				|| e instanceof EmployeeIdMissingException || e instanceof ManagerIdMissingException
				|| e instanceof InvalidLeaveIdException || e instanceof UnsupportedQueryParameterException
				|| e instanceof EmptyBodyForLeaveApprovalActionException || e instanceof InvalidLeaveActionTypeException
				|| e instanceof InvalidApproverIdException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();

		if (e instanceof InvalidEmployeeIdException) {
			response.setCode(Error_code.INVALID_EMPLOYEE_ID.toString());
			response.setHttp_code(Error_code.INVALID_EMPLOYEE_ID.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidDateException) {
			response.setCode(Error_code.INVALID_DATE.toString());
			response.setHttp_code(Error_code.INVALID_DATE.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof UnidentifiedParameterException) {
			response.setCode(Error_code.INVALID_INPUT_FORMAT.toString());
			response.setHttp_code(Error_code.INVALID_INPUT_FORMAT.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidLeaveTypeException) {
			response.setCode(Error_code.INVALID_LEAVE_TYPE.toString());
			response.setHttp_code(Error_code.INVALID_LEAVE_TYPE.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidHalfDayTypeException) {
			response.setCode(Error_code.INVALID_HALF_DAY_TYPE.toString());
			response.setHttp_code(Error_code.INVALID_HALF_DAY_TYPE.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveReasonEmptyException) {
			response.setCode(Error_code.REASON_IS_MISSING.toString());
			response.setHttp_code(Error_code.REASON_IS_MISSING.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof NumberFormatException) {
			response.setCode(Error_code.INVALID_NUMBER_FORMAT.toString());
			response.setHttp_code(Error_code.INVALID_NUMBER_FORMAT.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidLeaveIdException) {
			response.setCode(Error_code.INVALID_LEAVE_ID.toString());
			response.setHttp_code(Error_code.INVALID_LEAVE_ID.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidAuditActionTypeException) {
			response.setCode(Error_code.INVALID_AUDIT_ACTION.toString());
			response.setHttp_code(Error_code.INVALID_AUDIT_ACTION.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidAuditLeaveDaysException) {
			response.setCode(Error_code.INVALID_AUDIT_LEAVE_DAYS.toString());
			response.setHttp_code(Error_code.INVALID_AUDIT_LEAVE_DAYS.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveAuditCommentsNotFoundException) {
			response.setCode(Error_code.LEAVE_AUDIT_COMMENTS_NOT_FOUND.toString());
			response.setHttp_code(Error_code.LEAVE_AUDIT_COMMENTS_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidAuditManagerFlagException) {
			response.setCode(Error_code.INVALID_LEAVE_AUDIT_MANAGER_FLAG.toString());
			response.setHttp_code(Error_code.INVALID_LEAVE_AUDIT_MANAGER_FLAG.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveIdMissingException) {
			response.setCode(Error_code.LEAVE_ID_MISSING.toString());
			response.setHttp_code(Error_code.LEAVE_ID_MISSING.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof EmployeeIdMissingException) {
			response.setCode(Error_code.EMPLOYEE_ID_MISSING.toString());
			response.setHttp_code(Error_code.EMPLOYEE_ID_MISSING.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof ManagerIdMissingException) {
			response.setCode(Error_code.MANAGER_ID_MISSING.toString());
			response.setHttp_code(Error_code.MANAGER_ID_MISSING.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidLeaveIdException) {
			response.setCode(Error_code.INVALID_LEAVE_ID.toString());
			response.setHttp_code(Error_code.INVALID_LEAVE_ID.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof UnsupportedQueryParameterException) {
			response.setCode(Error_code.UNSUPPORTED_PARAMETER.toString());
			response.setHttp_code(Error_code.UNSUPPORTED_PARAMETER.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof EmptyBodyForLeaveApprovalActionException) {
			response.setCode(Error_code.DATA_NOT_FOUND_FOR_LEAVE_APPROVAL_ACTION.toString());
			response.setHttp_code(Error_code.DATA_NOT_FOUND_FOR_LEAVE_APPROVAL_ACTION.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidLeaveActionTypeException) {
			response.setCode(Error_code.INVALID_LEAVE_ACTION.toString());
			response.setHttp_code(Error_code.INVALID_LEAVE_ACTION.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidApproverIdException) {
			response.setCode(Error_code.INVALID_APPROVER_ID.toString());
			response.setHttp_code(Error_code.INVALID_APPROVER_ID.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
