package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DAO_employee_select;
import dto.DTO_department;
import dto.DTO_department_query_params;
import dto.DTO_employee;
import dto.DTO_employee_query_params;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.EmployeeTableEmptyException;
import exceptions.exception.InvalidDepartmentIdException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidLimitParameterException;
import exceptions.exception.InvalidOrderException;
import exceptions.exception.InvalidPageParameterException;
import exceptions.exception.InvalidSortByException;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.UnIdentifiedParameterException;
import exceptions.exception.UnknownDepartmentIdException;
import model.Employee;
import utils.dto.Utils_dto;
import utils.service.validator.Utils_service_department_validator;
import utils.service.validator.Utils_service_employee_validator;

public class Service_employee_get {

	private final DAO_employee_select employee_dao;

	public Service_employee_get(DAO_employee_select employee_dao) {
		this.employee_dao = employee_dao;
	}

	public List<DTO_employee> getAllEmployees() throws DataAccessException, EmployeeTableEmptyException {
		try {
			List<Employee> emp_list = this.employee_dao.getAllEmployee();
			List<DTO_employee> emp_dto_list = new ArrayList<DTO_employee>();

			for (Employee emp : emp_list) {
				emp_dto_list.add(Utils_dto.mapEmployeeToDTO(emp));
			}
			return emp_dto_list;

		} catch (SQLException e) {
			throw new DataAccessException("Database error occurred while retrieving employee details", e);
		}

	}

	public DTO_employee getEmployeeByID(Integer intID)
			throws DataAccessException, EmployeeIdMissingException, EmployeeNotFoundException {

		if (intID == null || intID <= 0) {
			throw new EmployeeIdMissingException("Invalid employee ID", null);
		}

		try {
			Employee emp = this.employee_dao.getEmployeeById(intID);
			return Utils_dto.mapEmployeeToDTO(emp);

		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching employee with ID " + intID, e);
		}
	}

	public List<DTO_employee> getEmployeesByQuery(DTO_employee_query_params query_params)
			throws InvalidLimitParameterException, InvalidPageParameterException, InvalidDepartmentIdException,
			UnknownDepartmentIdException, InvalidOrderException, InvalidSortByException, InvalidEmployeeEmailException,
			EmployeeTableEmptyException, EmployeeNotFoundException, DataAccessException {

		// validate limit and page
		if (query_params.getLimit() < 0) {
			throw new InvalidLimitParameterException("", null);
		}
		if (query_params.getPage() < 0) {
			throw new InvalidPageParameterException("", null);
		}

		// validate department
		if (query_params.getDepartmentId() != null)
			Utils_service_department_validator.validateDepartmentId(query_params.getDepartmentId());

		// validate sortBy and order
		Utils_service_employee_validator.validateSortByAndOrder(query_params);

		// validate emailId
		if (query_params.getEmail() != null)
			Utils_service_employee_validator.employeeEmailValidate(query_params.getEmail());

		List<Employee> emp_list;
		try {
			emp_list = this.employee_dao.getEmployeeByQuery(query_params);
			List<DTO_employee> emp_dto_list = new ArrayList<DTO_employee>();

			for (Employee emp : emp_list) {
				emp_dto_list.add(Utils_dto.mapEmployeeToDTO(emp));
			}
			return emp_dto_list;

		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching employees by query", e);

		}
	}

	public List<DTO_department> getAllDepartments() throws DataAccessException, EmployeeTableEmptyException {

		List<DTO_department> dep_list;
		try {
			dep_list = employee_dao.getAllDepartmentsDAO();
			return dep_list;

		} catch (SQLException e) {
			throw new DataAccessException("Data base error while fetching all departments", e);
		} catch (EmployeeTableEmptyException e) {
			throw e;
		}
	}

	public List<DTO_department> getDepartmentsByQuery(DTO_department_query_params query_params)
			throws DataAccessException, EmployeeTableEmptyException, UnIdentifiedParameterException {
		List<DTO_department> dep_list;
		try {
			dep_list = employee_dao.getDepartmentWithQueryDAO(query_params);
			return dep_list;
		} catch (SQLException e) {
			throw new DataAccessException("Data base error while fetching Department with query", e);
		} catch (EmployeeTableEmptyException e) {
			throw e;
		}
	}

	public DTO_employee getEmployeeByEmailId(String email)
			throws InvalidEmployeeEmailException, EmployeeNotFoundException, DataAccessException {

		// validate email_id
		if (email != null && !email.isBlank())
			Utils_service_employee_validator.employeeEmailValidate(email);

		try {
			Employee emp = this.employee_dao.getEmployeeByEmailId(email);
			return Utils_dto.mapEmployeeToDTO(emp);

		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching employee with email_id " + email, e);
		}
	}

}
