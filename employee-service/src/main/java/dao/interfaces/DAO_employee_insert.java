package dao.interfaces;

import java.sql.SQLException;

import model.Employee;

public interface DAO_employee_insert {

	public int InsertEmployeeDAO(Employee employee) throws SQLException;
	
}
