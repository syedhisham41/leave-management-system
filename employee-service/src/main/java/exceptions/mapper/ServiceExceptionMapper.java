package exceptions.mapper;

import constants.enums.Error_code;
import constants.enums.Error_code.error_code;
import constants.enums.Success_code.success_code;
import dto.DTO_api_response;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.EmployeeTableEmptyException;
import exceptions.exception.LeaveServiceUnreachableException;
import exceptions.exception.NoManagerFoundException;
import exceptions.exception.NoManagerFoundForDepartmentException;
import exceptions.exception.NoManagerFoundForEmployeeException;
import exceptions.exception.UnknownDepartmentIdException;

public class ServiceExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof EmployeeNotFoundException || e instanceof EmployeeTableEmptyException
				|| e instanceof LeaveServiceUnreachableException || e instanceof UnknownDepartmentIdException
				|| e instanceof NoManagerFoundForDepartmentException || e instanceof NoManagerFoundForEmployeeException
				|| e instanceof NoManagerFoundException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();

		if (e instanceof EmployeeNotFoundException) {
			response.setCode(Error_code.error_code.EMPLOYEE_NOT_FOUND.toString());
			response.setHttp_code(error_code.EMPLOYEE_NOT_FOUND.getHttp_code());
			response.setMessage(error_code.EMPLOYEE_NOT_FOUND.getMessage());
		}

		else if (e instanceof EmployeeTableEmptyException) {
			response.setCode(success_code.EMPLOYEE_LIST_EMPTY.toString());
			response.setHttp_code(success_code.EMPLOYEE_LIST_EMPTY.getHttp_code());
			response.setMessage(success_code.EMPLOYEE_LIST_EMPTY.getMessage());
		}

		else if (e instanceof LeaveServiceUnreachableException) {
			response.setCode(Error_code.error_code.LEAVE_SERVICE_UNREACHABLE.toString());
			response.setHttp_code(error_code.LEAVE_SERVICE_UNREACHABLE.getHttp_code());
			response.setMessage(error_code.LEAVE_SERVICE_UNREACHABLE.getMessage());
		}

		else if (e instanceof UnknownDepartmentIdException) {
			response.setCode(Error_code.error_code.UNKNOWN_DEPARTMENT.toString());
			response.setHttp_code(error_code.UNKNOWN_DEPARTMENT.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof NoManagerFoundForDepartmentException) {
			response.setCode(Error_code.error_code.MANAGER_NOT_FOUND.toString());
			response.setHttp_code(error_code.MANAGER_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof NoManagerFoundForEmployeeException) {
			response.setCode(Error_code.error_code.MANAGER_NOT_FOUND.toString());
			response.setHttp_code(error_code.MANAGER_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		else if (e instanceof NoManagerFoundException) {
			response.setCode(Error_code.error_code.MANAGER_NOT_FOUND.toString());
			response.setHttp_code(error_code.MANAGER_NOT_FOUND.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
