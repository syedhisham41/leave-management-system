package dao.impl.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.enums.Leave_types;
import dao.interfaces.DAO_leave_balance;
import db.DB_Connection;
import dto.DTO_leave_balance;
import exceptions.exception.LeaveBalanceNotFoundException;
import exceptions.exception.LeaveBalanceUpdateFailedException;

public class DAO_Impl_leave_balance_sqlite implements DAO_leave_balance {

	@Override
	public double getLeavebalance(int employee_id, Leave_types leaveType) throws SQLException {

		String sql = "SELECT balance FROM leave_balance WHERE employee_id = ? AND leave_type = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {
			preparedstmt.setInt(1, employee_id);
			preparedstmt.setString(2, leaveType.toString());

			ResultSet result = preparedstmt.executeQuery();

			if (result.next())
				return result.getDouble("balance");
			else {
				throw new SQLException(
						"No leave balance record found for employee " + employee_id + " and type " + leaveType);
			}
		}
	}

	@Override
	public int createLeaveBalance(int employee_id) throws SQLException {
		Map<Leave_types, Double> leaveMap = new HashMap<>();
		leaveMap.put(Leave_types.CASUAL, 10.0);
		leaveMap.put(Leave_types.SICK, 10.0);
		leaveMap.put(Leave_types.BEREAVEMENT, 5.0);
		leaveMap.put(Leave_types.PATERNITY, 10.0);
		leaveMap.put(Leave_types.MATERNITY, 180.0);
		leaveMap.put(Leave_types.EARNED, 5.0);

		String sql = "INSERT INTO leave_balance (employee_id, leave_type, balance) VALUES (?, ?, ?)";
		int totalInserted = 0;

		try (Connection conn = DB_Connection.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				for (Leave_types each : Leave_types.values()) {
					if (!leaveMap.containsKey(each)) {
						continue;
					}

					stmt.setInt(1, employee_id);
					stmt.setString(2, each.toString());
					stmt.setDouble(3, leaveMap.get(each));

					int rows = stmt.executeUpdate();
					if (rows != 1) {
						throw new SQLException("Failed to insert leave balance for type: " + each);
					}

					totalInserted += rows;
				}

				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		}
		return totalInserted;
	}

	@Override
	public List<DTO_leave_balance> getLeavebalanceForEmployee(int employee_id) throws SQLException {

		List<DTO_leave_balance> balance_list = new ArrayList<DTO_leave_balance>();
		String sql = "SELECT employee_id, leave_type, balance FROM leave_balance WHERE employee_id = ? ";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			preparedstmt.setInt(1, employee_id);

			ResultSet result = preparedstmt.executeQuery();

			while (result.next()) {
				DTO_leave_balance leave_balance = new DTO_leave_balance();
				leave_balance.setEmployee_id(result.getInt("employee_id"));
				leave_balance.setLeave_type(Leave_types.valueOf(result.getString("leave_type").toUpperCase()));
				leave_balance.setBalance(result.getDouble("balance"));

				balance_list.add(leave_balance);
			}

			if (!balance_list.isEmpty())
				return balance_list;
			else
				throw new SQLException("No leave balance record found for employee " + employee_id);
		}
	}

	@Override
	public int deleteLeaveBalance(int employee_id) throws SQLException, LeaveBalanceNotFoundException {

		String sql = "DELETE FROM leave_balance WHERE employee_id = ?";

		try (Connection conn = DB_Connection.getConnection();
				PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			preparedstmt.setInt(1, employee_id);

			int result = preparedstmt.executeUpdate();

			if (result > 0) {
				return result;
			} else
				throw new LeaveBalanceNotFoundException("No leave balance found for employee ID: " + employee_id, null);
		}
	}

	@Override
	public int updateLeaveBalance(Connection conn, int employee_id, Leave_types leave_type,
			double updated_leave_balance) throws SQLException, LeaveBalanceUpdateFailedException {
		String sql = "UPDATE leave_balance SET balance = ?, last_updated = datetime('now') WHERE employee_id = ? AND leave_type = ?";
		try (PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

			preparedstmt.setDouble(1, updated_leave_balance);
			preparedstmt.setInt(2, employee_id);
			preparedstmt.setString(3, leave_type.toString());

			int result = preparedstmt.executeUpdate();

			if (result > 0) {
				return result;
			} else
				throw new LeaveBalanceUpdateFailedException("Leave Balance update failed for employee_id :"
						+ employee_id + " leave_type :" + leave_type.toString(), null);
		}
	}
}
