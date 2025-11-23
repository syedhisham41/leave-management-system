package dao.impl.sqlite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DAO_department;
import dto.DTO_department;
import exceptions.exception.DepartmentNotFoundException;
import exceptions.exception.DepartmentTableEmptyException;

public class DAO_Impl_department_sqlite implements DAO_department {

	@Override
	public DTO_department getDepartment(String department_id) throws SQLException, DepartmentNotFoundException {

		String sql = "SELECT * FROM department WHERE LOWER(id) = LOWER(?)";

		try (Connection conn = db.DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			preparedstmt.setString(1, department_id);

			try (ResultSet result = preparedstmt.executeQuery()) {

				if (result.next()) {
					DTO_department department = new DTO_department();
					department.setDepartment_id(result.getString("id"));
					department.setDepartment_name(result.getString("name"));
					return department;
				} else {
					throw new DepartmentNotFoundException("Department not found in the DB", null);
				}
			}
		}
	}

	@Override
	public List<DTO_department> getAllDepartment() throws SQLException, DepartmentTableEmptyException {

		String sql = "SELECT * FROM department";

		try (Connection conn = db.DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			try (ResultSet result = preparedstmt.executeQuery()) {
				List<DTO_department> department_list = new ArrayList<DTO_department>();
				while (result.next()) {
					DTO_department department = new DTO_department();
					department.setDepartment_id(result.getString("id"));
					department.setDepartment_name(result.getString("name"));

					department_list.add(department);
				}

				if (!department_list.isEmpty()) {
					return department_list;
				} else {
					throw new DepartmentTableEmptyException("Department table is empty. No departments present");
				}
			}
		}
	}
}
