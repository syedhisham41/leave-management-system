package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import constants.enums.Half_day_types;
import constants.enums.Leave_types;
import dao.interfaces.DAO_leave_request_get;
import db.DB_Connection;
import dto.DTO_leave_request_get;
import exceptions.exception.DataAccessException;
import exceptions.exception.LeaveRequestNotFoundException;

public class DAO_Impl_get_leave_request_sqlite implements DAO_leave_request_get {

	@Override
	public List<DTO_leave_request_get> getLeaveRequestByEmployeeId(int employee_id)
			throws SQLException, DataAccessException {

		String sql = "SELECT * FROM leave_request WHERE employee_id = ?";

		List<DTO_leave_request_get> leave_request_list = new ArrayList<DTO_leave_request_get>();

		try (Connection conn = DB_Connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, employee_id);

			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				DTO_leave_request_get leave_request = new DTO_leave_request_get();
				leave_request.setLeave_id(result.getInt("leave_id"));
				leave_request.setEmployee_id(result.getInt("employee_id"));
				leave_request.setStart_date(result.getDate("start_date").toLocalDate());
				leave_request.setEnd_date(result.getDate("end_date").toLocalDate());
				leave_request.setHalf_day_type(Half_day_types.valueOf(result.getString("half_day_type")));
				leave_request.setLeave_type(Leave_types.valueOf(result.getString("leave_type")));
				leave_request.setStatus(result.getString("status"));

				Integer approverId = result.getInt("approver_id");
				leave_request.setApprover_id(result.wasNull() ? null : approverId);

				leave_request.setApplied_on(result.getTimestamp("applied_on").toString());

				Timestamp decisionTimestamp = result.getTimestamp("decision_on");
				leave_request.setDecision_on(decisionTimestamp != null ? decisionTimestamp.toString() : null);

				leave_request.setReason(result.getString("reason"));

				leave_request_list.add(leave_request);
			}

			if (!leave_request_list.isEmpty()) {
				return leave_request_list;
			} else
				return Collections.emptyList();

		} catch (SQLException e) {
			throw new DataAccessException("Leave Request get failed. Unexpected error from DB", null);
		}
	}
	
	@Override
	public List<DTO_leave_request_get> getLeaveRequestByEmployeeId(List<Integer> employee_id_list)
			throws SQLException, DataAccessException {

		if (employee_id_list == null || employee_id_list.isEmpty()) {
		    return Collections.emptyList();
		}
		
		String sql = "SELECT * FROM leave_request WHERE employee_id IN (" +
	             employee_id_list.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")";

		List<DTO_leave_request_get> leave_request_list = new ArrayList<DTO_leave_request_get>();

		try (Connection conn = DB_Connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			int index = 1;
			for (Integer empId : employee_id_list) {
			    stmt.setInt(index++, empId);
			}

			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				DTO_leave_request_get leave_request = new DTO_leave_request_get();
				leave_request.setLeave_id(result.getInt("leave_id"));
				leave_request.setEmployee_id(result.getInt("employee_id"));
				leave_request.setStart_date(result.getDate("start_date").toLocalDate());
				leave_request.setEnd_date(result.getDate("end_date").toLocalDate());
				leave_request.setHalf_day_type(Half_day_types.valueOf(result.getString("half_day_type")));
				leave_request.setLeave_type(Leave_types.valueOf(result.getString("leave_type")));
				leave_request.setStatus(result.getString("status"));

				Integer approverId = result.getInt("approver_id");
				leave_request.setApprover_id(result.wasNull() ? null : approverId);

				leave_request.setApplied_on(result.getTimestamp("applied_on").toString());

				Timestamp decisionTimestamp = result.getTimestamp("decision_on");
				leave_request.setDecision_on(decisionTimestamp != null ? decisionTimestamp.toString() : null);

				leave_request.setReason(result.getString("reason"));

				leave_request_list.add(leave_request);
			}

			if (!leave_request_list.isEmpty()) {
				return leave_request_list;
			} else
				return Collections.emptyList();

		} catch (SQLException e) {
			throw new DataAccessException("Leave Request get failed. Unexpected error from DB", null);
		}
	}

	@Override
	public DTO_leave_request_get getLeaveRequestByLeaveId(int leave_id)
			throws SQLException, DataAccessException, LeaveRequestNotFoundException {

		String sql = "SELECT * FROM leave_request WHERE leave_id = ?";

		try (Connection conn = DB_Connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, leave_id);

			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				DTO_leave_request_get leave_request = new DTO_leave_request_get();
				leave_request.setLeave_id(result.getInt("leave_id"));
				leave_request.setEmployee_id(result.getInt("employee_id"));
				leave_request.setStart_date(result.getDate("start_date").toLocalDate());
				leave_request.setEnd_date(result.getDate("end_date").toLocalDate());
				leave_request.setHalf_day_type(Half_day_types.valueOf(result.getString("half_day_type")));
				leave_request.setLeave_type(Leave_types.valueOf(result.getString("leave_type")));
				leave_request.setStatus(result.getString("status"));

				Integer approverId = result.getInt("approver_id");
				leave_request.setApprover_id(result.wasNull() ? null : approverId);

				leave_request.setApplied_on(result.getTimestamp("applied_on").toString());

				Timestamp decisionTimestamp = result.getTimestamp("decision_on");
				leave_request.setDecision_on(decisionTimestamp != null ? decisionTimestamp.toString() : null);

				leave_request.setReason(result.getString("reason"));
				return leave_request;
			} else {
				throw new LeaveRequestNotFoundException("leave request not found for leave_id : " + leave_id, null);
			}

		} catch (SQLException e) {
			throw new DataAccessException("Leave Request get failed. Unexpected error from DB", null);
		}
	}
}
