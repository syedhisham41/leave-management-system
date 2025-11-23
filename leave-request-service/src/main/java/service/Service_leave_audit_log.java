package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constants.enums.Leave_action;
import dao.interfaces.DAO_audit_log;
import dto.DTO_leave_audit_log;
import dto.DTO_leave_request_get;
import exceptions.exception.AuditLogUpdateFailedForApprovalException;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.InvalidAuditActionTypeException;
import exceptions.exception.InvalidAuditIdException;
import exceptions.exception.InvalidAuditLeaveDaysException;
import exceptions.exception.InvalidLeaveIdException;
import exceptions.exception.LeaveAuditCommentsNotFoundException;
import exceptions.exception.LeaveAuditLogNotFoundException;
import exceptions.exception.LeaveAuditLogTableEmptyException;
import exceptions.exception.LeaveIdDoesntExistException;
import exceptions.exception.LeaveRequestNotFoundException;
import exceptions.exception.ManagerNotFoundException;
import utils.service.proxy.Utils_employee_service_proxy;
import utils.service.validator.Utils_service_leave_audit_validator;

public class Service_leave_audit_log {

	public final DAO_audit_log audit_log;
	public final Service_leave_request_get leave_request_service;

	public Service_leave_audit_log(DAO_audit_log audit_log, Service_leave_request_get leave_request_service) {
		this.audit_log = audit_log;
		this.leave_request_service = leave_request_service;
	}

	public int postAuditLog(DTO_leave_audit_log audit_log, boolean leaveIdValidateRequired,
			boolean performedByIdValidateRequired) throws SQLException, DataAccessException,
			LeaveRequestNotFoundException, InvalidAuditActionTypeException, EmployeeNotFoundException,
			LeaveAuditCommentsNotFoundException, InvalidAuditLeaveDaysException, InvalidLeaveIdException {

		int result = 0;
		if (leaveIdValidateRequired) {
			this.leave_request_service.getLeaveRequestByLeaveId(audit_log.getLeave_id());
		}

		boolean leave_audit_validate = Utils_service_leave_audit_validator.leaveAuditValidatorForPost(audit_log,
				performedByIdValidateRequired);
		if (leave_audit_validate)
			result = this.audit_log.postAuditLog(audit_log);

		return result;

	}

	public List<DTO_leave_audit_log> getAuditLogByEmployeeId(int employee_id, boolean manager,
			boolean isEmployeeValidateRequired) throws EmployeeNotFoundException, DataAccessException,
			ManagerNotFoundException, SQLException, LeaveRequestNotFoundException, LeaveAuditLogNotFoundException {

		List<DTO_leave_request_get> leave_request_list = new ArrayList<>();
		if (isEmployeeValidateRequired && !manager) {
			Utils_employee_service_proxy.isEmployeeValid(employee_id);
		}

		// manager validation
		if (manager) {

			try {
				leave_request_list = this.leave_request_service.getLeaveRequestByManagerId(employee_id);
			} catch (LeaveRequestNotFoundException e) {
				throw new LeaveAuditLogNotFoundException("no audit log found for the manager :" + employee_id, null);

			}
			List<Integer> leave_ids = new ArrayList<>();

			for (DTO_leave_request_get leave_request : leave_request_list) {
				leave_ids.add(leave_request.getLeave_id());
			}

			return this.audit_log.getAuditLogForLeaveId(leave_ids);

		}

		leave_request_list = this.leave_request_service.getLeaveRequestByEmployeeId(employee_id);
		List<Integer> leave_ids = new ArrayList<>();

		for (DTO_leave_request_get leave_request : leave_request_list) {
			leave_ids.add(leave_request.getLeave_id());
		}

		return this.audit_log.getAuditLogForLeaveId(leave_ids);
	}

	public DTO_leave_audit_log getAuditLogByLeaveId(int leave_id)
			throws InvalidLeaveIdException, SQLException, DataAccessException, EmployeeNotFoundException,
			LeaveIdDoesntExistException, LeaveAuditLogNotFoundException {

		// validate leave_id
		if (leave_id < 0)
			throw new InvalidLeaveIdException("leave_id is invalid", null);
		try {
			this.leave_request_service.getLeaveRequestByLeaveId(leave_id);
		} catch (LeaveRequestNotFoundException e) {
			throw new LeaveIdDoesntExistException("Leave Id doesnt exist. ", null);
		}

		return this.audit_log.getAuditLogForLeaveId(leave_id);
	}

	public List<DTO_leave_audit_log> getAllAuditLog() throws LeaveAuditLogTableEmptyException, DataAccessException {

		return this.audit_log.getAllAuditLog();
	}

	// connection is passed here as part of transaction
	public int updateAuditLogActionByApproval(Connection conn, DTO_leave_audit_log audit_log)
			throws InvalidAuditIdException, LeaveAuditCommentsNotFoundException,
			AuditLogUpdateFailedForApprovalException, DataAccessException {

		if (audit_log.getAudit_id() < 0)
			throw new InvalidAuditIdException("Audit Log Id is invalid", null);

		if (audit_log.getAction() == Leave_action.REJECTED && audit_log.getApprover_comments().isEmpty())
			throw new LeaveAuditCommentsNotFoundException("Comment not found for REJECTED leave action", null);

		try {
			return this.audit_log.updateAuditLog(conn, audit_log);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}
}
