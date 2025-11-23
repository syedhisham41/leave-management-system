package service;

import java.sql.SQLException;

import dao.interfaces.DAO_employee_update;
import exceptions.exception.DataAccessException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidEmployeeJoiningDateException;
import exceptions.exception.InvalidEmployeeNameException;
import exceptions.exception.UnknownDepartmentIdException;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.InvalidDepartmentIdException;
import model.Employee;
import utils.service.validator.Utils_service_department_validator;
import utils.service.validator.Utils_service_employee_validator;

public class Service_employee_update {

	private DAO_employee_update employee_update_dao;

	public Service_employee_update(DAO_employee_update employee_update_dao) {
		this.employee_update_dao = employee_update_dao;
	}

	public int updateEmployee(int id, Employee emp) throws DataAccessException, EmployeeIdMissingException,
			InvalidEmployeeNameException, InvalidEmployeeEmailException, InvalidEmployeeJoiningDateException,
			InvalidDepartmentIdException, UnknownDepartmentIdException {

		if (id <= 0)
			throw new EmployeeIdMissingException("invalid id", null);

		Utils_service_employee_validator.employeeNameValidate(emp.getName());

		Utils_service_employee_validator.employeeEmailValidate(emp.getEmail());

		Utils_service_employee_validator.employeeJoiningDateValidate(emp.getJoining_date());

		Utils_service_department_validator.validateDepartmentId(emp.getEmp_department_id());

		try {
			return this.employee_update_dao.updateEmployeeDAO(id, emp);
		} catch (SQLException e) {
			throw new DataAccessException("failed to update employee with Id " + id, e);
		}
	}

}
