package service;

import java.sql.Connection;
import java.sql.SQLException;

import constants.enums.Leave_action;
import dao.interfaces.DAO_leave_request_update;
import dto.DTO_leave_request_get;
import exceptions.exception.DataAccessException;
import exceptions.exception.LeaveApprovalCommentsNotFoundException;
import exceptions.exception.LeaveRequestApprovalFailedException;

public class Service_leave_request_update {

	public final DAO_leave_request_update leave_request_update_dao;

	public Service_leave_request_update(DAO_leave_request_update leave_request_update_dao) {
		this.leave_request_update_dao = leave_request_update_dao;
	}

	// connection is passed here as part of transaction
	public int updateLeaveRequestStatusByApproval(Connection conn, DTO_leave_request_get leave_request)
			throws LeaveRequestApprovalFailedException, DataAccessException, LeaveApprovalCommentsNotFoundException {

		int result = 0;

		if (leave_request.getStatus().equalsIgnoreCase(Leave_action.REJECTED.toString())
				&& (leave_request.getApprover_comments().isEmpty() || leave_request.getApprover_comments().isBlank())) {
			throw new LeaveApprovalCommentsNotFoundException("Approver Comments are required for REJECTED scenario",
					null);
		}
		// verify if status is valid
		try {
			result = this.leave_request_update_dao.update_leave_request_dao(conn, leave_request);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), null);
		}
		return result;

	}
}
