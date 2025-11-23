package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import constants.enums.Leave_action;
import dao.interfaces.DAO_audit_log;
import db.DB_Connection;
import db.DB_Parameters;
import dto.DTO_leave_audit_log;
import exceptions.exception.AuditLogUpdateFailedForApprovalException;
import exceptions.exception.DataAccessException;
import exceptions.exception.LeaveAuditLogNotFoundException;
import exceptions.exception.LeaveAuditLogTableEmptyException;

public class DAO_Impl_audit_log_sqlite implements DAO_audit_log {

	@Override
	public int postAuditLog(DTO_leave_audit_log audit_log) throws SQLException {

		String sql = "INSERT into leave_audit_log (leave_id, action, performed_by, comments, leave_days) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedstmt.setInt(1, audit_log.getLeave_id());
			preparedstmt.setString(2, audit_log.getAction().toString());
			preparedstmt.setInt(3, audit_log.getPerformed_by());
			preparedstmt.setString(4, audit_log.getComments());
			preparedstmt.setDouble(5, audit_log.getLeave_days());

			preparedstmt.executeUpdate();

			try (ResultSet result = preparedstmt.getGeneratedKeys()) {
				if (result.next()) {
					return result.getInt(1);
				} else {
					throw new SQLException("Failed to retrieve generated audit ID");
				}
			}
		}
	}

	@Override
	public List<DTO_leave_audit_log> getAuditLogForLeaveId(List<Integer> leave_ids) throws DataAccessException {

		if (leave_ids == null || leave_ids.isEmpty()) {
			return Collections.emptyList();
		}

		String sql = "SELECT * FROM leave_audit_log WHERE leave_id IN ("
				+ leave_ids.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")";

		List<DTO_leave_audit_log> leave_audit_list = new ArrayList<>();

		try (Connection conn = DB_Connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			int index = 1;
			for (Integer leaveId : leave_ids) {
				stmt.setInt(index++, leaveId);
			}

			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				DTO_leave_audit_log leave_audit = new DTO_leave_audit_log();

				leave_audit.setLeave_id(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_LEAVE_ID));
				leave_audit.setAction(Leave_action.valueOf(result.getString(DB_Parameters.LEAVE_AUDIT_COL_ACTION)));
				leave_audit.setComments(result.getString(DB_Parameters.LEAVE_AUDIT_COL_COMMENTS));
				leave_audit.setLeave_days(result.getDouble(DB_Parameters.LEAVE_AUDIT_COL_LEAVE_DAYS));
				leave_audit.setPerformed_by(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_PERFORMED_BY));
				leave_audit.setPerformed_on(result.getTimestamp(DB_Parameters.LEAVE_AUDIT_COL_PERFORMED_ON));
				leave_audit.setAudit_id(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_AUDIT_ID));

				leave_audit_list.add(leave_audit);

			}

			if (!leave_audit_list.isEmpty()) {
				return leave_audit_list;
			} else
				return Collections.emptyList();

		} catch (SQLException e) {
			throw new DataAccessException("Leave Audit get failed. Unexpected error from DB", null);
		}
	}

	@Override
	public DTO_leave_audit_log getAuditLogForLeaveId(int leave_id)
			throws LeaveAuditLogNotFoundException, DataAccessException {

		String sql = "SELECT * FROM leave_audit_log WHERE leave_id = ?";

		try (Connection conn = DB_Connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, leave_id);

			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				DTO_leave_audit_log leave_audit = new DTO_leave_audit_log();

				leave_audit.setLeave_id(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_LEAVE_ID));
				leave_audit.setAction(Leave_action.valueOf(result.getString(DB_Parameters.LEAVE_AUDIT_COL_ACTION)));
				leave_audit.setComments(result.getString(DB_Parameters.LEAVE_AUDIT_COL_COMMENTS));
				leave_audit.setLeave_days(result.getDouble(DB_Parameters.LEAVE_AUDIT_COL_LEAVE_DAYS));
				leave_audit.setPerformed_by(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_PERFORMED_BY));
				leave_audit.setPerformed_on(result.getTimestamp(DB_Parameters.LEAVE_AUDIT_COL_PERFORMED_ON));
				leave_audit.setAudit_id(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_AUDIT_ID));

				return leave_audit;
			}

			else
				throw new LeaveAuditLogNotFoundException("Leave Audit Log not found for leave_id :" + leave_id, null);

		} catch (SQLException e) {
			throw new DataAccessException("Leave Audit get failed. Unexpected error from DB", null);
		}
	}

	@Override
	public List<DTO_leave_audit_log> getAllAuditLog() throws LeaveAuditLogTableEmptyException, DataAccessException {

		String sql = "SELECT * FROM leave_audit_log";
		List<DTO_leave_audit_log> audit_log_list = new ArrayList<>();

		try (Connection conn = DB_Connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				DTO_leave_audit_log leave_audit = new DTO_leave_audit_log();

				leave_audit.setLeave_id(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_LEAVE_ID));
				leave_audit.setAction(Leave_action.valueOf(result.getString(DB_Parameters.LEAVE_AUDIT_COL_ACTION)));
				leave_audit.setComments(result.getString(DB_Parameters.LEAVE_AUDIT_COL_COMMENTS));
				leave_audit.setLeave_days(result.getDouble(DB_Parameters.LEAVE_AUDIT_COL_LEAVE_DAYS));
				leave_audit.setPerformed_by(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_PERFORMED_BY));
				leave_audit.setPerformed_on(result.getTimestamp(DB_Parameters.LEAVE_AUDIT_COL_PERFORMED_ON));
				leave_audit.setAudit_id(result.getInt(DB_Parameters.LEAVE_AUDIT_COL_AUDIT_ID));

				audit_log_list.add(leave_audit);
			}

			if (audit_log_list != null && !audit_log_list.isEmpty())
				return audit_log_list;
			else
				throw new LeaveAuditLogTableEmptyException("Leave Audit Log table is empty", null);

		} catch (SQLException e) {
			throw new DataAccessException("Leave Audit get failed. Unexpected error from DB", null);
		}
	}

	@Override
	public int updateAuditLog(Connection conn, DTO_leave_audit_log audit_log)
			throws SQLException, AuditLogUpdateFailedForApprovalException {
		String sql = "UPDATE leave_audit_log SET performed_by = ?, comments = ?, action = ?, approver_comments = ?,  performed_on = datetime('now') WHERE audit_id = ?";
		try (PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			preparedstmt.setInt(1, audit_log.getPerformed_by());
			preparedstmt.setString(2, audit_log.getComments());
			preparedstmt.setString(3, audit_log.getAction().toString());
			preparedstmt.setString(4, audit_log.getApprover_comments());
			preparedstmt.setInt(5, audit_log.getAudit_id());

			int result = preparedstmt.executeUpdate();

			if (result > 0) {
				return result;
			} else {
				throw new AuditLogUpdateFailedForApprovalException(
						"Audit log update failed during approval of leave request", null);
			}
		}
	}
}
