package utils.service.validator;

import java.util.List;

import dto.DTO_department_manager;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.InvalidManagerIdException;
import exceptions.exception.NoManagerFoundForEmployeeException;
import service.Service_manager;

public class Utils_service_manager_validator {

	public static List<DTO_department_manager> validateManagerId(Integer manager_id, Service_manager manager_service)
			throws InvalidManagerIdException, NumberFormatException, NoManagerFoundForEmployeeException,
			DataAccessException, EmployeeIdMissingException, EmployeeNotFoundException {

		// null check
		if (manager_id < 0 || manager_id == null)
			throw new InvalidManagerIdException("manager ID is null or invalid", null);

		// validate if managerId is a manager and manager is an employee
		return manager_service.getManagerByEmployeeId(manager_id);

	}
}
