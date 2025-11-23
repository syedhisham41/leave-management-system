package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.interfaces.DAO_leave_request_post;
import db.DB_Connection;
import dto.DTO_leave_request;

public class DAO_Impl_post_leave_request_sqlite implements DAO_leave_request_post {

	@Override
	public int post_leave_request_dao(DTO_leave_request leave_request) throws SQLException {

		String sql = "INSERT INTO leave_request (employee_id, start_date, end_date, leave_type, half_day_type, reason) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedstmt.setInt(1, leave_request.getEmployee_id());
			preparedstmt.setDate(2, Date.valueOf(leave_request.getStart_date()));
			preparedstmt.setDate(3, Date.valueOf(leave_request.getEnd_date()));
			preparedstmt.setString(4, leave_request.getLeave_type().toString());
			preparedstmt.setString(5, leave_request.getHalf_day_type().toString());
			preparedstmt.setString(6, leave_request.getReason());

			preparedstmt.executeUpdate();

			try (ResultSet resultSet = preparedstmt.getGeneratedKeys()) {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				} else {
					throw new SQLException("Failed to retrieve generated Leave Request ID");
				}
			}
		}
	}

	public boolean post_leave_request_overlap_check_dao(DTO_leave_request leave_request) throws SQLException {

		String sql = "SELECT 1 FROM leave_request WHERE employee_id = ? AND start_date <= ? AND end_date >= ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {
			preparedstmt.setInt(1, leave_request.getEmployee_id());
			preparedstmt.setDate(2, Date.valueOf(leave_request.getEnd_date()));
			preparedstmt.setDate(3, Date.valueOf(leave_request.getStart_date()));

			ResultSet result = preparedstmt.executeQuery();
			return result.next();
		}
	}
}
