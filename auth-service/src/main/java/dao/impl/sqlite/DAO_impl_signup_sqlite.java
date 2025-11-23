package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import constants.enums.Auth_role;
import dao.interfaces.DAO_Signup;
import db.DB_Connection;
import db.DB_Parameters;
import dto.DTO_user_record;
import exceptions.exception.DataAccessException;
import exceptions.exception.UserRecordNotFoundException;
import exceptions.exception.UserRecordDeleteFailedException;
import model.User;

public class DAO_impl_signup_sqlite implements DAO_Signup {

	@Override
	public int signup(User user) throws SQLException, DataAccessException {
		String sql = "INSERT INTO users (emp_id, email, password_hash, salt) VALUES (?, ?, ?, ?)";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(sql)) {

			preparedStmt.setInt(1, user.getEmp_id());
			preparedStmt.setString(2, user.getEmail());
			preparedStmt.setString(3, user.getPassword_hash());
			preparedStmt.setString(4, user.getSalt());

			int result = preparedStmt.executeUpdate();

			if (result > 0)
				return result;
			else
				throw new DataAccessException("Error occured during DB insert", null);
		}
	}

	@Override
	public DTO_user_record getUserRecord(int employee_id) throws SQLException, UserRecordNotFoundException {

		String sql = "SELECT * FROM users WHERE emp_id = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(sql)) {

			preparedStmt.setInt(1, employee_id);

			ResultSet result = preparedStmt.executeQuery();

			if (result.next()) {
				return new DTO_user_record(result.getInt(DB_Parameters.AUTH_USER_COL_EMP_ID),
						result.getString(DB_Parameters.AUTH_USER_COL_EMAIL),
						Auth_role.valueOf(result.getString(DB_Parameters.AUTH_USER_COL_ROLE)));
			} else
				throw new UserRecordNotFoundException("The user record is not found in the DB", null);
		}
	}

	@Override
	public int deleteUserRecord(int employee_id) throws SQLException, UserRecordDeleteFailedException {
		String sql = "DELETE FROM users WHERE emp_id = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(sql)) {

			preparedStmt.setInt(1, employee_id);

			int result = preparedStmt.executeUpdate();

			if (result > 0) {
				return result;
			} else
				throw new UserRecordDeleteFailedException("The user record delete operation failed in the DB", null);
		}
	}
}
