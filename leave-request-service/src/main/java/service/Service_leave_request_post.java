package service;

import java.sql.SQLException;

import constants.enums.Half_day_types;
import constants.enums.Leave_action;
import dao.interfaces.DAO_leave_balance;
import dao.interfaces.DAO_leave_request_post;
import dto.DTO_leave_audit_log;
import dto.DTO_leave_request;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.InsufficientLeaveBalanceException;
import exceptions.exception.InvalidAuditActionTypeException;
import exceptions.exception.InvalidAuditLeaveDaysException;
import exceptions.exception.InvalidDateException;
import exceptions.exception.InvalidLeaveIdException;
import exceptions.exception.LeaveAuditCommentsNotFoundException;
import exceptions.exception.LeaveOverlappingException;
import exceptions.exception.LeaveRequestNotFoundException;
import exceptions.exception.UnsupportedHalfDayTypeException;
import utils.service.Utils_service;
import utils.service.proxy.Utils_employee_service_proxy;

public class Service_leave_request_post {

	public final DAO_leave_request_post leave_request_post_dao;
	public final DAO_leave_balance leave_balance;
	public final Service_leave_audit_log audit_log_service;

	public Service_leave_request_post(DAO_leave_request_post leave_request_post_dao, DAO_leave_balance leave_balance,
			Service_leave_audit_log audit_log_service) {
		this.leave_request_post_dao = leave_request_post_dao;
		this.leave_balance = leave_balance;
		this.audit_log_service = audit_log_service;
	}

	public int postLeaveRequest(DTO_leave_request leave_request) throws SQLException, InvalidDateException,
			EmployeeNotFoundException, DataAccessException, LeaveOverlappingException, UnsupportedHalfDayTypeException,
			InsufficientLeaveBalanceException, LeaveRequestNotFoundException, InvalidAuditActionTypeException,
			LeaveAuditCommentsNotFoundException, InvalidAuditLeaveDaysException, InvalidLeaveIdException {

		int result = 0;
		int employee_id = leave_request.getEmployee_id();
		Utils_employee_service_proxy.isEmployeeValid(employee_id);

		if (leave_request.getEnd_date().isBefore(leave_request.getStart_date())) {
			throw new InvalidDateException("'end_date is before start_date. Please check the dates'", null);
		}

		if (leave_request.getStart_date().isEqual(leave_request.getEnd_date())
				&& (leave_request.getHalf_day_type().equals(Half_day_types.FIRST_DAY_HALF)
						|| leave_request.getHalf_day_type().equals(Half_day_types.LAST_DAY_HALF))) {
			throw new UnsupportedHalfDayTypeException("HalfDayType unsupported as leave requested for only 1 day",
					null);
		}

		double leaveDays = Utils_service.getNumberofLeaveDays(leave_request);

		// leave balance can be avoided for wfh scenario TODO
		// check for leave balance
		try {
			if (this.leave_balance.getLeavebalance(employee_id, leave_request.getLeave_type()) < leaveDays)
				throw new InsufficientLeaveBalanceException("No sufficient leave balance", null);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), null);
		}

		// check for overlapping leaves
		if (this.leave_request_post_dao.post_leave_request_overlap_check_dao(leave_request))
			throw new LeaveOverlappingException("leave request is overlapping", null);

		try {
			result = this.leave_request_post_dao.post_leave_request_dao(leave_request);
			DTO_leave_audit_log audit_log = new DTO_leave_audit_log();
			audit_log.setLeave_id(result);
			audit_log.setAction(Leave_action.APPLIED);
			audit_log.setPerformed_by(employee_id);
			audit_log.setComments(leave_request.getReason());
			audit_log.setLeave_days(leaveDays);
			this.audit_log_service.postAuditLog(audit_log, false, false);
			return result;

		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), null);
		}

	}
}
