package utils.handler.validator;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import constants.enums.Half_day_types;
import constants.enums.Leave_types;
import exceptions.exception.*;

public class Utils_handler_leave_request_validator {

	public static String[] supportedEmployeeIdKeys = { "employee_id", "employeeid" };

	public static String[] supportedManagerIdKeys = { "manager_id", "managerid" };

	public static String[] supportedLeaveIdKeys = { "leave_id", "leaveid" };

	public static int getEmployeeIdFromQueryAfterValidation(Map<String, String> params)
			throws EmployeeIdMissingException, UnidentifiedParameterException, InvalidEmployeeIdException {

		for (String key : supportedEmployeeIdKeys) {
			if (params.containsKey(key)) {
				String employee_id = params.get(key);
				try {
					if (employee_id != null && !employee_id.isBlank())
						return Integer.parseInt(employee_id);
				} catch (NumberFormatException e) {
					throw new InvalidEmployeeIdException("employee_id is of invalid format", e);
				}
				throw new EmployeeIdMissingException(
						"Missing or blank value for required query parameter: 'employee_id' or 'employeeid'", null);
			}
		}
		throw new UnidentifiedParameterException("Unrecognized query parameter. Expected 'employee_id' or 'employeeid'", null);
	}

	public static int getManagerIdFromQueryAfterValidation(Map<String, String> params)
			throws UnidentifiedParameterException, ManagerIdMissingException, InvalidManagerIdException {

		for (String key : supportedManagerIdKeys) {
			if (params.containsKey(key)) {
				String manager_id = params.get(key);
				try {
					if (manager_id != null && !manager_id.isBlank())
						return Integer.parseInt(manager_id);
				} catch (NumberFormatException e) {
					throw new InvalidManagerIdException("manager_id is of invalid format", e);
				}
				throw new ManagerIdMissingException(
						"Missing or blank value for required query parameter: 'manager_id' or 'managerid'", null);
			}
		}
		throw new UnidentifiedParameterException("Unrecognized query parameter. Expected 'manager_id' or 'managerid'",
				null);
	}

	public static int getLeaveIdFromQueryAfterValidation(Map<String, String> params)
			throws InvalidLeaveIdException, LeaveIdMissingException, UnidentifiedParameterException {

		for (String key : supportedLeaveIdKeys) {
			if (params.containsKey(key)) {
				String leave_id = params.get(key);
				try {
					if (leave_id != null && !leave_id.isBlank())
						return Integer.parseInt(leave_id);
				} catch (NumberFormatException e) {
					throw new InvalidLeaveIdException("leave_id is of invalid format", e);
				}
				throw new LeaveIdMissingException(
						"Missing or blank value for required query parameter: 'leave_id' or 'leaveid'", null);
			}
		}
		throw new UnidentifiedParameterException("Unrecognized query parameter. Expected 'leave_id' or 'leaveid'",
				null);
	}

	public static boolean leaveRequestValidatorForPost(JsonNode node) throws InvalidEmployeeIdException,
			UnsupportedEncodingException, InvalidDateException, InvalidLeaveTypeException, InvalidHalfDayTypeException,
			LeaveReasonEmptyException, UnidentifiedParameterException {

		if (node == null || node.isEmpty())
			return false;

		final List<String> allowedFields = List.of("employee_id", "start_date", "end_date", "leave_type",
				"half_day_type", "reason");

		Iterator<String> fields = node.fieldNames();
		while (fields.hasNext()) {
			String field = fields.next();
			if (!allowedFields.contains(field)) {
				throw new UnidentifiedParameterException("Unexpected field: " + field, null);
			}
		}

		final List<Leave_types> leaveTypeListToCheckReason = List.of(Leave_types.CASUAL, Leave_types.EARNED);

		Leave_types leave_type;

		// Validate employee_id
		if (!node.has("employee_id") || !node.get("employee_id").canConvertToInt()) {
			throw new InvalidEmployeeIdException("'employee_id' field is missing or invalid", null);
		}

		// Validate start_date
		if (!node.has("start_date")) {
			throw new InvalidDateException("'start_date' field is missing", null);
		} else {
			isValidDate("start_date", node.get("start_date").asText());
		}

		// Validate end_date
		if (!node.has("end_date")) {
			throw new InvalidDateException("'end_date' field is missing", null);
		} else {
			isValidDate("end_date", node.get("end_date").asText());
		}

		// Validate leave_type
		if (!node.has("leave_type")) {
			throw new InvalidLeaveTypeException("'leave_type' field is missing", null);
		}
		try {
			leave_type = Leave_types.valueOf(node.get("leave_type").asText().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidLeaveTypeException("'leave_type' field is invalid", null);
		}

		// Validate half_day_type
		if (!node.has("half_day_type")) {
			throw new InvalidHalfDayTypeException("'half_day_type' field is missing", null);
		}
		try {
			Half_day_types.valueOf(node.get("half_day_type").asText().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidHalfDayTypeException("'half_day_type' field is invalid", null);
		}

		// Validate reason
		if (leaveTypeListToCheckReason.contains(leave_type)) {
			if (!node.has("reason") || node.get("reason").asText().trim().isEmpty()) {
				throw new LeaveReasonEmptyException("'reason' is required for leave_type: " + leave_type, null);
			}
		}

		return true;
	}

	public static boolean isValidDate(String dateField, String date) throws InvalidDateException {
		try {
			LocalDate.parse(date);
			return true;
		} catch (DateTimeParseException e) {
			throw new InvalidDateException(dateField + " is not a valid date format ", null);
		}
	}
}
