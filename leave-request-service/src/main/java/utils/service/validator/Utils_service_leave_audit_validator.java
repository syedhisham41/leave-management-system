package utils.service.validator;

import java.util.Arrays;

import constants.enums.Leave_action;
import dto.DTO_leave_audit_log;
import exceptions.exception.*;
import utils.service.proxy.Utils_employee_service_proxy;

public class Utils_service_leave_audit_validator {

	public static boolean leaveAuditValidatorForPost(DTO_leave_audit_log audit_log,
			boolean performedByIdValidateRequired) throws InvalidAuditActionTypeException, EmployeeNotFoundException,
			DataAccessException, LeaveAuditCommentsNotFoundException, InvalidAuditLeaveDaysException {

		// checking if action type is supported value from the enum
		boolean isValid = Arrays.stream(Leave_action.values()).anyMatch(a -> a == audit_log.getAction());
		if (!isValid)
			throw new InvalidAuditActionTypeException("Action type is not supported", null);

		// check if performed_by is a valid employee_id or manager_id
		if (performedByIdValidateRequired)
			Utils_employee_service_proxy.isEmployeeValid(audit_log.getPerformed_by());

		// check if comments are present : mandatory for REJECTED action type
		if (audit_log.getAction() == Leave_action.REJECTED
				&& (audit_log.getComments() == null || audit_log.getComments().trim().isEmpty()))
			throw new LeaveAuditCommentsNotFoundException("Comments is required if leave request is REJECTED", null);

		// check the leavedays
		if (audit_log.getLeave_days() == 0)
			throw new InvalidAuditLeaveDaysException("Applied leave days cannot be zero", null);

		return true;
	}
}
