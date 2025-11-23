package dao.impl.sqlite;

import java.sql.*;

import dao.interfaces.DAO_employee_update;
import db.*;
import model.*;


public class DAO_Impl_employee_update_sqlite implements DAO_employee_update {
	
	@Override
	public int updateEmployeeDAO(int id, Employee employee) throws SQLException{
		String sql = "UPDATE employees SET emp_name = ?, emp_joining_date = ?, emp_department = ?, emp_email = ?, updated_at = datetime('now') WHERE emp_id = ?";
		
		try(Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)){
			preparedstmt.setString(1, employee.getName());
			preparedstmt.setString(2, employee.getJoining_date());
			preparedstmt.setString(3, employee.getEmp_department_id());
			preparedstmt.setString(4, employee.getEmail());
			preparedstmt.setInt(5, id);
			
			
			int result = preparedstmt.executeUpdate();
			if (result == 1)return result;
			else {
				throw new SQLException("update unsuccessful");
			}
		}
	}
}
