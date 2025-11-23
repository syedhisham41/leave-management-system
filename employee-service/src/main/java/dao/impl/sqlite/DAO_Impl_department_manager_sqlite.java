package dao.impl.sqlite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import constants.enums.Employee_role;
import dao.interfaces.DAO_manager;
import db.DB_Parameters;
import exceptions.exception.DepartmentManagerTableEmptyException;
import model.DepartmentManager;

public class DAO_Impl_department_manager_sqlite implements DAO_manager {

	@Override
	public List<DepartmentManager> getManagerByDept(String department_id)
			throws SQLException, DepartmentManagerTableEmptyException {

		String sql = "SELECT * FROM department_manager WHERE LOWER(department_id) = LOWER(?)";

		try (Connection conn = db.DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			preparedstmt.setString(1, department_id);

			try (ResultSet result = preparedstmt.executeQuery()) {

				List<DepartmentManager> list = new ArrayList<>();
				while (result.next()) {
					DepartmentManager manager = new DepartmentManager();
					manager.setManager_id(result.getInt(DB_Parameters.MNGR_COL_MNGR_ID));
					manager.setDepartment_id(result.getString(DB_Parameters.MNGR_COL_DEPT_ID));
					manager.setRole(Employee_role.valueOf(result.getString(DB_Parameters.MNGR_COL_ROLE)));
					manager.setCreated_at(result.getTimestamp(DB_Parameters.MNGR_COL_CREATED_AT));
					manager.setUpdated_at(result.getTimestamp(DB_Parameters.MNGR_COL_UPDATED_AT));

					list.add(manager);
				}
				if (!list.isEmpty()) {
					return list;
				} else {
					throw new DepartmentManagerTableEmptyException(
							"Department-Manager table is empty. No managers present");
				}
			}
		}
	}

	@Override
	public List<DepartmentManager> getAllManagers() throws SQLException, DepartmentManagerTableEmptyException {

		String sql = "SELECT * FROM department_manager";

		try (Connection conn = db.DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			try (ResultSet result = preparedstmt.executeQuery()) {

				List<DepartmentManager> list = new ArrayList<>();
				while (result.next()) {
					DepartmentManager manager = new DepartmentManager();
					manager.setManager_id(result.getInt(DB_Parameters.MNGR_COL_MNGR_ID));
					manager.setDepartment_id(result.getString(DB_Parameters.MNGR_COL_DEPT_ID));
					manager.setRole(Employee_role.valueOf(result.getString(DB_Parameters.MNGR_COL_ROLE)));
					manager.setCreated_at(result.getTimestamp(DB_Parameters.MNGR_COL_CREATED_AT));
					manager.setUpdated_at(result.getTimestamp(DB_Parameters.MNGR_COL_UPDATED_AT));

					list.add(manager);
				}
				if (!list.isEmpty()) {
					return list;
				} else {
					throw new DepartmentManagerTableEmptyException(
							"Department-Manager table is empty. No managers present");
				}
			}
		}
	}
}
