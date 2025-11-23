package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dao.interfaces.DAO_leave_request_update;
import dto.DTO_leave_request_get;
import exceptions.exception.LeaveRequestApprovalFailedException;

public class DAO_Impl_update_leave_request_sqlite implements DAO_leave_request_update {

	@Override
	public int update_leave_request_dao(Connection conn, DTO_leave_request_get leave_request)
			throws SQLException, LeaveRequestApprovalFailedException {

		String sql = "UPDATE leave_request SET approver_id = ?, status = ?, reason = ?, decision_on = datetime('now'), approver_comments = ? WHERE leave_id = ?";
		try (PreparedStatement preparedstmt = conn.prepareStatement(sql)) {
			preparedstmt.setInt(1, leave_request.getApprover_id());
			preparedstmt.setString(2, leave_request.getStatus());
			preparedstmt.setString(3, leave_request.getReason());
			preparedstmt.setString(4, leave_request.getApprover_comments());
			preparedstmt.setInt(5, leave_request.getLeave_id());

			int affectedRows = preparedstmt.executeUpdate();

			if (affectedRows > 0) {
				return affectedRows;
			}
			throw new LeaveRequestApprovalFailedException(
					"Leave request update failed for leave_id" + leave_request.getLeave_id(), null);
		}
	}
}
