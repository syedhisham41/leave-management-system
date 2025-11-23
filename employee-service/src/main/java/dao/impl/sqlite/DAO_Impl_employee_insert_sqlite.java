package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.interfaces.DAO_employee_insert;
import db.DB_Connection;
import model.Employee;

public class DAO_Impl_employee_insert_sqlite implements DAO_employee_insert{
	
	@Override
	public int InsertEmployeeDAO(Employee employee) throws SQLException{
		String sql = "INSERT INTO employees (emp_name, emp_joining_date, emp_department_id, emp_email) VALUES (?, ?, ?, ?)";
		
		try(Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			preparedstmt.setString(1, employee.getName());
			preparedstmt.setString(2, employee.getJoining_date());
			preparedstmt.setString(3, employee.getEmp_department_id());
			preparedstmt.setString(4, employee.getEmail());
			
			preparedstmt.executeUpdate();
			
			try(ResultSet result = preparedstmt.getGeneratedKeys()){
				if(result.next()) {
					return result.getInt(1);
				}
				else {
					throw new SQLException("Failed to retrieve generated Employee ID");
				}
			}
		}
	}
}
