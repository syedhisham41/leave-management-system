package dao.impl.sqlite;

import java.sql.*;

import dao.interfaces.DAO_employee_delete;
import db.*;
import exceptions.exception.EmployeeNotFoundException;

public class DAO_Impl_employee_delete_sqlite implements DAO_employee_delete {

	@Override
	public int deleteEmployeeDAO(int id) throws SQLException, EmployeeNotFoundException {
		String sql = "DELETE FROM employees WHERE emp_id = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {
			preparedstmt.setInt(1, id);

			int result = preparedstmt.executeUpdate();
			if (result > 0) {
				return result;
			} else
				throw new EmployeeNotFoundException("No employee found for employee ID");
		}
	}

}
