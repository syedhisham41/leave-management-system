package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import dto.DTO_department;
import dto.DTO_department_query_params;
import dto.DTO_employee_query_params;
import exceptions.exception.*;
import model.*;

public interface DAO_employee_select {

	public Employee getEmployeeById(int Id) throws SQLException, EmployeeNotFoundException;

	public List<Employee> getAllEmployee() throws SQLException, EmployeeTableEmptyException;

	public List<Employee> getEmployeeByQuery(DTO_employee_query_params query_params)
			throws SQLException, EmployeeTableEmptyException, InvalidOrderException, InvalidSortByException, EmployeeNotFoundException;

	public List<DTO_department> getAllDepartmentsDAO() throws SQLException, EmployeeTableEmptyException;

	public List<DTO_department> getDepartmentWithQueryDAO(DTO_department_query_params query_params)
			throws SQLException, EmployeeTableEmptyException, UnIdentifiedParameterException, DataAccessException;

	public Employee getEmployeeByEmailId(String email) throws SQLException, EmployeeNotFoundException;

}
