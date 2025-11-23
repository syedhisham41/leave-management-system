package exceptions.mapper;

import constants.enums.Error_code;
import constants.enums.Success_code;
import dto.DTO_api_response;
import exceptions.exception.ApproverIsNotManagerException;
import exceptions.exception.ApproverIsNotManagerOfEmployeeException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.EmployeeTableEmptyException;
import exceptions.exception.InsufficientLeaveBalanceException;
import exceptions.exception.InvalidAuditIdException;
import exceptions.exception.LeaveApprovalCommentsNotFoundException;
import exceptions.exception.LeaveAuditLogNotFoundException;
import exceptions.exception.LeaveIdDoesntExistException;
import exceptions.exception.LeaveOverlappingException;
import exceptions.exception.LeaveRequestNotFoundException;
import exceptions.exception.LeaveStatusNotPendingStateException;
import exceptions.exception.ManagerNotFoundException;
import exceptions.exception.UnsupportedHalfDayTypeException;

public class ServiceExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof EmployeeNotFoundException || e instanceof EmployeeTableEmptyException
				|| e instanceof LeaveOverlappingException || e instanceof UnsupportedHalfDayTypeException
				|| e instanceof InsufficientLeaveBalanceException || e instanceof LeaveRequestNotFoundException
				|| e instanceof ManagerNotFoundException || e instanceof LeaveIdDoesntExistException
				|| e instanceof LeaveAuditLogNotFoundException || e instanceof LeaveStatusNotPendingStateException
				|| e instanceof ApproverIsNotManagerException || e instanceof ApproverIsNotManagerOfEmployeeException
				|| e instanceof InvalidAuditIdException || e instanceof LeaveApprovalCommentsNotFoundException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();

		if (e instanceof EmployeeNotFoundException) {
			response.setCode(Error_code.EMPLOYEE_NOT_FOUND.toString());
			response.setHttp_code(Error_code.EMPLOYEE_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof EmployeeTableEmptyException) {
			response.setCode(Success_code.EMPLOYEE_LIST_EMPTY.toString());
			response.setHttp_code(Success_code.EMPLOYEE_LIST_EMPTY.getHttp_code());
			response.setMessage(Success_code.EMPLOYEE_LIST_EMPTY.getMessage());
		}

		else if (e instanceof LeaveOverlappingException) {
			response.setCode(Error_code.OVERLAPPING_LEAVE_REQUEST.toString());
			response.setHttp_code(Error_code.OVERLAPPING_LEAVE_REQUEST.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof UnsupportedHalfDayTypeException) {
			response.setCode(Error_code.HALF_DAY_UNSUPPORTED.toString());
			response.setHttp_code(Error_code.HALF_DAY_UNSUPPORTED.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InsufficientLeaveBalanceException) {
			response.setCode(Error_code.INSUFFICIENT_LEAVE_BALANCE.toString());
			response.setHttp_code(Error_code.INSUFFICIENT_LEAVE_BALANCE.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveRequestNotFoundException) {
			response.setCode(Error_code.LEAVE_REQUEST_NOT_FOUND.toString());
			response.setHttp_code(Error_code.LEAVE_REQUEST_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof ManagerNotFoundException) {
			response.setCode(Error_code.MANAGER_NOT_FOUND.toString());
			response.setHttp_code(Error_code.MANAGER_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveIdDoesntExistException) {
			response.setCode(Error_code.LEAVE_ID_DOESNT_EXIST.toString());
			response.setHttp_code(Error_code.LEAVE_ID_DOESNT_EXIST.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveAuditLogNotFoundException) {
			response.setCode(Error_code.AUDIT_LOG_NOT_FOUND.toString());
			response.setHttp_code(Error_code.AUDIT_LOG_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof LeaveStatusNotPendingStateException) {
			response.setCode(Error_code.INVALID_LEAVE_STATUS.toString());
			response.setHttp_code(Error_code.INVALID_LEAVE_STATUS.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof ApproverIsNotManagerException) {
			response.setCode(Error_code.APPROVER_IS_NOT_MANAGER.toString());
			response.setHttp_code(Error_code.APPROVER_IS_NOT_MANAGER.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof ApproverIsNotManagerOfEmployeeException) {
			response.setCode(Error_code.UNKNOWN_MANAGER_FOR_EMPLOYEE.toString());
			response.setHttp_code(Error_code.UNKNOWN_MANAGER_FOR_EMPLOYEE.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof InvalidAuditIdException) {
			response.setCode(Error_code.INVALID_AUDIT_ID.toString());
			response.setHttp_code(Error_code.INVALID_AUDIT_ID.getHttp_code());
			response.setMessage(e.getMessage());
		}
		
		else if (e instanceof LeaveApprovalCommentsNotFoundException) {
			response.setCode(Error_code.LEAVE_APPROVAL_COMMENTS_NOT_FOUND.toString());
			response.setHttp_code(Error_code.LEAVE_APPROVAL_COMMENTS_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
