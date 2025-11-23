package service;

import java.sql.SQLException;
import java.util.List;

import dao.interfaces.DAO_manager;
import dto.DTO_department_manager;
import dto.DTO_employee;
import dto.DTO_employee_query_params;
import dto.DTO_manager_check;
import exceptions.exception.DataAccessException;
import exceptions.exception.DepartmentManagerTableEmptyException;
import exceptions.exception.DepartmentNotFoundException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.EmployeeTableEmptyException;
import exceptions.exception.InvalidDepartmentIdException;
import exceptions.exception.InvalidEmployeeEmailException;
import exceptions.exception.InvalidLimitParameterException;
import exceptions.exception.InvalidManagerIdException;
import exceptions.exception.InvalidOrderException;
import exceptions.exception.InvalidPageParameterException;
import exceptions.exception.InvalidSortByException;
import exceptions.exception.NoManagerFoundException;
import exceptions.exception.NoManagerFoundForDepartmentException;
import exceptions.exception.NoManagerFoundForEmployeeException;
import exceptions.exception.EmployeeIdMissingException;
import exceptions.exception.UnknownDepartmentIdException;
import model.DepartmentManager;
import utils.dto.Utils_dto;

public class Service_manager {

	private final DAO_manager manager_dao;
	private final Service_department department_service;
	private final Service_employee_get employee_service;

	public Service_manager(DAO_manager manager_dao, Service_department department_service,
			Service_employee_get employee_service) {
		this.manager_dao = manager_dao;
		this.department_service = department_service;
		this.employee_service = employee_service;
	}

	public List<DTO_department_manager> getManagerByDept(String department_id)
			throws InvalidDepartmentIdException, DataAccessException, UnknownDepartmentIdException,
			DepartmentNotFoundException, DepartmentManagerTableEmptyException, NoManagerFoundForDepartmentException {

		// validate department
		this.department_service.getDepartment(department_id);

		List<DepartmentManager> manager_list;
		try {
			manager_list = this.manager_dao.getManagerByDept(department_id);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} catch (DepartmentManagerTableEmptyException e) {
			throw new NoManagerFoundForDepartmentException("No manager mapped for Department id : " + department_id,
					null);
		}

		return Utils_dto.mapManagerToDTO(manager_list);
	}

	public List<DTO_department_manager> getManagerByEmployeeId(int employee_id) throws DataAccessException,
			EmployeeIdMissingException, EmployeeNotFoundException, NoManagerFoundForEmployeeException {

		// validate employee id
		DTO_employee employee = this.employee_service.getEmployeeByID(employee_id);

		List<DepartmentManager> manager_list;
		try {
			manager_list = this.manager_dao.getManagerByDept(employee.getDepartment());
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} catch (DepartmentManagerTableEmptyException e) {
			throw new NoManagerFoundForEmployeeException("No manager mapped for Employee id : " + employee_id, null);
		}

		return Utils_dto.mapManagerToDTO(manager_list);
	}

	public List<DTO_department_manager> getAllManagers()
			throws DepartmentManagerTableEmptyException, DataAccessException {

		List<DepartmentManager> manager_list;
		try {
			manager_list = this.manager_dao.getAllManagers();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}

		return Utils_dto.mapManagerToDTO(manager_list);
	}

	public List<DTO_employee> getEmployeesByManagerId(Integer manager_id, DTO_employee_query_params qp)
			throws DataAccessException, EmployeeIdMissingException, EmployeeNotFoundException,
			InvalidManagerIdException, NoManagerFoundException, InvalidLimitParameterException,
			InvalidPageParameterException, InvalidDepartmentIdException, UnknownDepartmentIdException,
			InvalidOrderException, InvalidSortByException, InvalidEmployeeEmailException, EmployeeTableEmptyException {

		List<DTO_department_manager> list;
		// validate managerId
		if (manager_id == null || manager_id < 0)
			throw new InvalidManagerIdException("manager ID is null or invalid", null);

		try {
			list = getManagerByEmployeeId(manager_id);
		} catch (NoManagerFoundForEmployeeException e) {
			throw new NoManagerFoundException("No manager found with manager_id :" + manager_id, null);

		}

//		DTO_employee_query_params emp_query = new DTO_employee_query_params();
		qp.setDepartmentId(list.get(0).getDepartment_id());

		List<DTO_employee> employees_list = this.employee_service.getEmployeesByQuery(qp);

		DTO_employee manager_employee = this.employee_service.getEmployeeByID(manager_id);
		employees_list.removeIf(emp -> emp.getEmail_id().equalsIgnoreCase(manager_employee.getEmail_id()));

		return employees_list;

	}

	public DTO_manager_check getManagerCheckInfo(int employee_id) throws DataAccessException,
			EmployeeIdMissingException, EmployeeNotFoundException, SQLException, DepartmentManagerTableEmptyException {

		// validate employee id
		this.employee_service.getEmployeeByID(employee_id);

		List<DepartmentManager> manager_list = this.manager_dao.getAllManagers();

		for (DepartmentManager manager : manager_list) {
			if (manager.getManager_id() == employee_id) {
				return new DTO_manager_check(true, manager.getDepartment_id(), employee_id, manager.getRole());
			}
		}
		return new DTO_manager_check(false, null, employee_id, null);
	}
}
