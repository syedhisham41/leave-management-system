package dao.interfaces;

import java.sql.SQLException;

import model.Employee;

public interface DAO_employee_update {

	public int updateEmployeeDAO(int id, Employee employee) throws SQLException;
	
}
