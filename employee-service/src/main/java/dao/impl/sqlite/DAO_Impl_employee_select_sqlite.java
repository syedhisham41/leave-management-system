package dao.impl.sqlite;

import java.sql.*;
import java.util.*;

import dao.interfaces.DAO_employee_select;
import db.*;
import dto.DTO_department;
import dto.DTO_department_query_params;
import dto.DTO_employee_query_params;
import exceptions.exception.*;
import model.*;
import utils.dao.Utils_dao;

public class DAO_Impl_employee_select_sqlite implements DAO_employee_select {

	@Override
	public Employee getEmployeeById(int Id) throws SQLException, EmployeeNotFoundException {

		String sql = "SELECT * FROM employees WHERE emp_id = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql);) {
			preparedstmt.setInt(1, Id);

			try (ResultSet resultSet = preparedstmt.executeQuery()) {
				if (resultSet.next()) {
					Employee emp = new Employee();
					emp.setId(resultSet.getInt(DB_Parameters.EMP_COL_ID));
					emp.setName(resultSet.getString(DB_Parameters.EMP_COL_NAME));
					emp.setJoining_date(resultSet.getString(DB_Parameters.EMP_COL_JOINING_DATE));
					emp.setEmp_department_id(resultSet.getString(DB_Parameters.EMP_COL_DEPARTMENT));
					emp.setEmail(resultSet.getString(DB_Parameters.EMP_COL_EMAIL));
					return emp;
				} else {
					throw new EmployeeNotFoundException("Employee with ID " + Id + " not found");
				}
			}
		}
	}

	@Override
	public List<Employee> getAllEmployee() throws SQLException, EmployeeTableEmptyException {
		String sql = "SELECT * FROM employees";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql);) {

			try (ResultSet resultSet = preparedstmt.executeQuery()) {
				List<Employee> emp_list = new ArrayList<>();
				while (resultSet.next()) {

					Employee emp = new Employee();
					emp.setId(resultSet.getInt(DB_Parameters.EMP_COL_ID));
					emp.setName(resultSet.getString(DB_Parameters.EMP_COL_NAME));
					emp.setJoining_date(resultSet.getString(DB_Parameters.EMP_COL_JOINING_DATE));
					emp.setEmp_department_id(resultSet.getString(DB_Parameters.EMP_COL_DEPARTMENT));
					emp.setEmail(resultSet.getString(DB_Parameters.EMP_COL_EMAIL));

					emp_list.add(emp);

				}
				if (!emp_list.isEmpty()) {
					return emp_list;
				} else {
					throw new EmployeeTableEmptyException("Employee table is empty. No Employees present");
				}
			}
		}
	}

	@Override
	public List<Employee> getEmployeeByQuery(DTO_employee_query_params query_params) throws SQLException,
			EmployeeTableEmptyException, InvalidSortByException, InvalidOrderException, EmployeeNotFoundException {

		Map<String, Object> filters = new HashMap<>();
		int offset = 0;

		if (query_params.getDepartmentId() != null && !query_params.getDepartmentId().isEmpty()) {
			filters.put("department", query_params.getDepartmentId());
		}
		if (query_params.getSortBy() != null && !query_params.getSortBy().isEmpty()) {
			filters.put("sortby", query_params.getSortBy());
		}
		if (query_params.getOrder() != null && !query_params.getOrder().isEmpty()) {
			filters.put("order", query_params.getOrder());
		}
		if (query_params.getEmail() != null && !query_params.getEmail().isEmpty()) {
			filters.put("email", query_params.getEmail());
		}

		if (query_params.getPage() != 0) {
			filters.put("page", query_params.getPage());
		}
		if (query_params.getLimit() != 0) {
			filters.put("limit", query_params.getLimit());
		}

		String sql = Utils_dao.sqlBuilder(filters);

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			int idx = 1;
			if (filters.containsKey("department"))
				preparedstmt.setString(idx++, (String) filters.get("department"));
			if (filters.containsKey("email"))
				preparedstmt.setString(idx++, (String) filters.get("email"));
			if (filters.containsKey("limit"))
				preparedstmt.setInt(idx++, (int) filters.get("limit"));
			if (filters.containsKey("page")) {
				offset = ((int) filters.get("page") - 1) * (int) filters.get("limit");
				preparedstmt.setInt(idx++, offset);
			}

			try (ResultSet resultSet = preparedstmt.executeQuery()) {
				List<Employee> emp_list = new ArrayList<Employee>();
				while (resultSet.next()) {
					Employee emp = new Employee();
					emp.setId(resultSet.getInt(DB_Parameters.EMP_COL_ID));
					emp.setName(resultSet.getString(DB_Parameters.EMP_COL_NAME));
					emp.setJoining_date(resultSet.getString(DB_Parameters.EMP_COL_JOINING_DATE));
					emp.setEmp_department_id(resultSet.getString(DB_Parameters.EMP_COL_DEPARTMENT));
					emp.setEmail(resultSet.getString(DB_Parameters.EMP_COL_EMAIL));

					emp_list.add(emp);
				}
				if (!emp_list.isEmpty()) {
					return emp_list;
				} else {
//					throw new EmployeeTableEmptyException("Employee table is empty. No Employees present");
					throw new EmployeeNotFoundException("Employee table is empty. No Employees present");
				}
			}
		}
	}

	@Override
	public List<DTO_department> getAllDepartmentsDAO() throws SQLException, EmployeeTableEmptyException {
		String sql = "SELECT DISTINCT emp_department_id FROM employees ORDER BY emp_department_id ASC";

		List<DTO_department> dep_list = new ArrayList<DTO_department>();
		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			try (ResultSet resultSet = preparedstmt.executeQuery()) {
				while (resultSet.next()) {
					DTO_department dep = new DTO_department();
					dep.setDepartment_name(resultSet.getString(DB_Parameters.EMP_COL_DEPARTMENT));

					dep_list.add(dep);
				}

				if (!dep_list.isEmpty()) {
					return dep_list;
				} else {
					throw new EmployeeTableEmptyException("Department list is empty");
				}
			}
		}
	}

	@Override
	public List<DTO_department> getDepartmentWithQueryDAO(DTO_department_query_params query_params)
			throws SQLException, EmployeeTableEmptyException, UnIdentifiedParameterException, DataAccessException {

		String sql = null;
		boolean sortByValidate = false;
		int offset = (query_params.getPage() - 1) * query_params.getLimit();
		List<DTO_department> dep_list = new ArrayList<DTO_department>();
		try {
			sortByValidate = Utils_dao.validateSortByAndOrder(query_params);
		} catch (InvalidOrderException e) {
			throw new UnIdentifiedParameterException("'Order' parameter is invalid", e);
		} catch (InvalidSortByException e) {
			throw new UnIdentifiedParameterException("'SortBy' parameter is invalid", e);
		}

		if (sortByValidate)
			sql = "SELECT DISTINCT emp_department_id FROM employees WHERE emp_department_id LIKE LOWER(?) ORDER BY "
					+ query_params.getSortBy() + " " + query_params.getOrder().toUpperCase() + " LIMIT ? OFFSET ?";
		else
			sql = "SELECT DISTINCT emp_department_id FROM employees WHERE emp_department_id LIKE LOWER(?) LIMIT ? OFFSET ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {
			int index = 1;
			preparedstmt.setString(index++, query_params.getSearch());
			preparedstmt.setInt(index++, query_params.getLimit());
			preparedstmt.setInt(index++, offset);

			try (ResultSet resultSet = preparedstmt.executeQuery()) {
				while (resultSet.next()) {
					DTO_department dep = new DTO_department();
					dep.setDepartment_id(resultSet.getString(DB_Parameters.EMP_COL_DEPARTMENT));
					dep_list.add(dep);
				}
				return dep_list;
			} catch (SQLException e) {
				throw new DataAccessException("department query failed", null);
			}
		}

	}

	@Override
	public Employee getEmployeeByEmailId(String email) throws SQLException, EmployeeNotFoundException {

		String sql = "SELECT * FROM employees WHERE emp_email = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql);) {
			preparedstmt.setString(1, email);

			try (ResultSet resultSet = preparedstmt.executeQuery()) {
				if (resultSet.next()) {
					Employee emp = new Employee();
					emp.setId(resultSet.getInt(DB_Parameters.EMP_COL_ID));
					emp.setName(resultSet.getString(DB_Parameters.EMP_COL_NAME));
					emp.setJoining_date(resultSet.getString(DB_Parameters.EMP_COL_JOINING_DATE));
					emp.setEmp_department_id(resultSet.getString(DB_Parameters.EMP_COL_DEPARTMENT));
					emp.setEmail(resultSet.getString(DB_Parameters.EMP_COL_EMAIL));
					return emp;
				} else {
					throw new EmployeeNotFoundException("Employee with email_id " + email + " not found");
				}
			}
		}
	}
}
