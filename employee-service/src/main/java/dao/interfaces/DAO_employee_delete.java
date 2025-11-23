package dao.interfaces;

import java.sql.SQLException;

import exceptions.exception.EmployeeNotFoundException;

public interface DAO_employee_delete {
	
	public int deleteEmployeeDAO(int id) throws SQLException, EmployeeNotFoundException;

}
