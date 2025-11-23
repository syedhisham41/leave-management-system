package service;

import java.sql.Connection;
import java.sql.SQLException;

import constants.enums.Leave_action;
import constants.enums.Leave_status;
import db.DB_Connection;
import dto.DTO_leave_audit_log;
import dto.DTO_leave_request_get;
import exceptions.exception.ApproverIsNotManagerException;
import exceptions.exception.ApproverIsNotManagerOfEmployeeException;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.InsufficientLeaveBalanceException;
import exceptions.exception.InvalidLeaveIdException;
import exceptions.exception.LeaveApprovalCommentsNotFoundException;
import exceptions.exception.LeaveAuditCommentsNotFoundException;
import exceptions.exception.LeaveRequestNotFoundException;
import exceptions.exception.LeaveStatusNotPendingStateException;
import exceptions.exception.ManagerNotFoundException;
import utils.service.proxy.Utils_employee_service_proxy;
import utils.service.validator.Utils_service_leave_approval_validator;

public class Service_leave_request_approval {

	public final Service_leave_request_get leave_request_service;
	public final Service_leave_request_update leave_request_update_service;
	public final Service_leave_balance leave_balance_service;
	public final Service_leave_audit_log leave_audit_service;

	public Service_leave_request_approval(Service_leave_request_get leave_request_service,
			Service_leave_request_update leave_request_update_service, Service_leave_balance leave_balance_service,
			Service_leave_audit_log leave_audit_service) {
		this.leave_request_service = leave_request_service;
		this.leave_request_update_service = leave_request_update_service;
		this.leave_balance_service = leave_balance_service;
		this.leave_audit_service = leave_audit_service;
	}

	public void approveLeaveRequest(int leave_id, int approver_id, String comments)
			throws SQLException, DataAccessException, EmployeeNotFoundException, InvalidLeaveIdException,
			LeaveRequestNotFoundException, LeaveStatusNotPendingStateException, ManagerNotFoundException,
			ApproverIsNotManagerException, ApproverIsNotManagerOfEmployeeException, InsufficientLeaveBalanceException {

		// validate leave id
		DTO_leave_request_get leave_request = this.leave_request_service.getLeaveRequestByLeaveId(leave_id);

		// validate leave request is in pending state
		if (!leave_request.getStatus().equalsIgnoreCase(Leave_status.PENDING.toString()))
			throw new LeaveStatusNotPendingStateException("Leave Status is not in PENDING state for Approval", null);

		// validate leave balance
		double currentBalance = this.leave_balance_service.getLeaveBalance(leave_request.getEmployee_id(),
				leave_request.getLeave_type(), true);
		if (leave_request.getLeave_days() > currentBalance)
			throw new InsufficientLeaveBalanceException("No sufficient leave balance", null);

		// validate approver is a manager
		if (!Utils_employee_service_proxy.isManagerValid(approver_id))
			throw new ApproverIsNotManagerException("Approver is not a manager", null);

		// validate if the manager is the manager of the employee in question
		if (!Utils_service_leave_approval_validator.verifyIfApproverIsManagerOfEmployee(leave_request, approver_id))
			throw new ApproverIsNotManagerOfEmployeeException(
					"Approver is not manager of the employee with leave_id :" + leave_request.getLeave_id(), null);

		Connection conn = null;

		try {
			// creating a transaction to ensure atomic operations
			conn = DB_Connection.getConnection();
			conn.setAutoCommit(false);

			// set approver id in leave request
			// change the status of leave to approved
			leave_request.setApprover_id(approver_id);
			leave_request.setStatus(Leave_status.APPROVED.toString());
			leave_request.setApprover_comments(comments);
			this.leave_request_update_service.updateLeaveRequestStatusByApproval(conn, leave_request);

			// change the leave balance of the specific leave type
			double updatedBalance = currentBalance - leave_request.getLeave_days();
			this.leave_balance_service.updateLeaveBalanceByApproval(conn, leave_request, updatedBalance, true);

			// change the audit Log to approved
			DTO_leave_audit_log audit_log = this.leave_audit_service.getAuditLogByLeaveId(leave_id);
			audit_log.setAction(Leave_action.APPROVED);
			audit_log.setComments(leave_request.getReason());
			audit_log.setPerformed_by(approver_id);
			audit_log.setApprover_comments(comments);
			this.leave_audit_service.updateAuditLogActionByApproval(conn, audit_log);

		} catch (Exception e) {

			if (conn != null)
				conn.rollback();
			throw new DataAccessException("Transaction failed : " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
	}

	public void rejectLeaveRequest(int leave_id, int approver_id, String comments)
			throws SQLException, DataAccessException, EmployeeNotFoundException, InvalidLeaveIdException,
			LeaveRequestNotFoundException, LeaveStatusNotPendingStateException, LeaveAuditCommentsNotFoundException,
			LeaveApprovalCommentsNotFoundException, ApproverIsNotManagerException, ManagerNotFoundException,
			ApproverIsNotManagerOfEmployeeException {

		if (comments.isBlank() || comments.isEmpty())
			throw new LeaveApprovalCommentsNotFoundException("comments are required for Leave Rejection ", null);

		// validate leave id
		DTO_leave_request_get leave_request = this.leave_request_service.getLeaveRequestByLeaveId(leave_id);

		// validate leave request is in pending state
		if (!leave_request.getStatus().equalsIgnoreCase(Leave_status.PENDING.toString()))
			throw new LeaveStatusNotPendingStateException("Leave Status is not in PENDING state for Approval", null);

		// validate approver is a manager
		if (!Utils_employee_service_proxy.isManagerValid(approver_id))
			throw new ApproverIsNotManagerException("Approver is not a manager", null);

		// validate if the manager is the manager of the employee in question
		if (!Utils_service_leave_approval_validator.verifyIfApproverIsManagerOfEmployee(leave_request, approver_id))
			throw new ApproverIsNotManagerOfEmployeeException(
					"Approver is not manager of the employee with leave_id :" + leave_request.getLeave_id(), null);
		
		
		Connection conn = null;

		try {
			// creating a transaction to ensure atomic operations
			conn = DB_Connection.getConnection();
			conn.setAutoCommit(false);

			// set approver id in leave request
			// change the status of leave to approved
			leave_request.setApprover_id(approver_id);
			leave_request.setStatus(Leave_status.REJECTED.toString());
			leave_request.setApprover_comments(comments);
			this.leave_request_update_service.updateLeaveRequestStatusByApproval(conn, leave_request);

			// change the audit Log to rejected
			DTO_leave_audit_log audit_log = this.leave_audit_service.getAuditLogByLeaveId(leave_id);
			audit_log.setAction(Leave_action.REJECTED);
			audit_log.setComments(leave_request.getReason());
			audit_log.setPerformed_by(approver_id);
			audit_log.setApprover_comments(comments);
			this.leave_audit_service.updateAuditLogActionByApproval(conn, audit_log);

		} catch (Exception e) {

			if (conn != null)
				conn.rollback();
			throw new DataAccessException("Transaction failed : " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}

	}

	public void cancelLeaveRequest(int leave_id, int approver_id, String comments) {

	}

}
