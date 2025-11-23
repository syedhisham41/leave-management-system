package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import constants.enums.Auth_role;
import dao.interfaces.DAO_Login;
import db.DB_Connection;
import db.DB_Parameters;
import dto.DTO_user_login_request;
import exceptions.exception.LastLoginUpdateFailedException;
import exceptions.exception.UserNotFoundException;
import model.User;

public class DAO_impl_login_sqlite implements DAO_Login {

	@Override
	public User login(DTO_user_login_request user_details) throws SQLException, UserNotFoundException {
		String sql = "SELECT * FROM users WHERE email = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(sql)) {

			preparedStmt.setString(1, user_details.getEmail());

			ResultSet result = preparedStmt.executeQuery();

			if (result.next()) {
				User user = new User();
				user.setEmp_id(result.getInt(DB_Parameters.AUTH_USER_COL_EMP_ID));
				user.setEmail(result.getString(DB_Parameters.AUTH_USER_COL_EMAIL));
				user.setRole(Auth_role.valueOf(result.getString(DB_Parameters.AUTH_USER_COL_ROLE)));
				user.setIs_active(result.getBoolean(DB_Parameters.AUTH_USER_COL_IS_ACTIVE));
				user.setPassword_hash(result.getString(DB_Parameters.AUTH_USER_COL_PASSWORD));
				user.setSalt(result.getString(DB_Parameters.AUTH_USER_COL_SALT));
				user.setCreated_at(result.getTimestamp(DB_Parameters.AUTH_USER_COL_CREATED_AT));
				user.setUpdated_at(result.getTimestamp(DB_Parameters.AUTH_USER_COL_UPDATED_AT));
				user.setLast_login_at(result.getTimestamp(DB_Parameters.AUTH_USER_COL_LAST_LOGIN));

				return user;
			} else {
				throw new UserNotFoundException("No User found in the table with email Id : " + user_details.getEmail(),
						null);
			}
		}
	}

	@Override
	public int updateLastLogin(int employee_id) throws SQLException, LastLoginUpdateFailedException {
		String sql = "UPDATE users SET last_login_at = CURRENT_TIMESTAMP WHERE emp_id = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(sql)) {

			preparedStmt.setInt(1, employee_id);

			int result = preparedStmt.executeUpdate();

			if (result > 0)
				return result;
			else
				throw new LastLoginUpdateFailedException("Last login update failed after login", null);
		}
	}
}
