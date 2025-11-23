package dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import dto.DTO_leave_request_get;
import exceptions.exception.LeaveRequestApprovalFailedException;

public interface DAO_leave_request_update {

	public int update_leave_request_dao(Connection conn, DTO_leave_request_get leave_request)
			throws SQLException, LeaveRequestApprovalFailedException;

}
