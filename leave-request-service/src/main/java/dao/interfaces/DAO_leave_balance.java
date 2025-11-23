package dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import constants.enums.Leave_types;
import dto.DTO_leave_balance;
import exceptions.exception.LeaveBalanceNotFoundException;
import exceptions.exception.LeaveBalanceUpdateFailedException;

public interface DAO_leave_balance {

	public double getLeavebalance(int employee_id, Leave_types leaveType) throws SQLException;

	public int createLeaveBalance(int employee_id) throws SQLException;

	public List<DTO_leave_balance> getLeavebalanceForEmployee(int employee_id) throws SQLException;

	public int deleteLeaveBalance(int employee_id) throws SQLException, LeaveBalanceNotFoundException;

	public int updateLeaveBalance(Connection conn, int employee_id, Leave_types leave_type,
			double updated_leave_balance) throws SQLException, LeaveBalanceUpdateFailedException;

}
