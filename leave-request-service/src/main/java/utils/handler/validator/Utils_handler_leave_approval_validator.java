package utils.handler.validator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.fasterxml.jackson.databind.JsonNode;

import constants.enums.Leave_action;
import exceptions.exception.*;

public class Utils_handler_leave_approval_validator {

	private static final Map<String, Leave_action> actionMap = Map.ofEntries(
			Map.entry("approve", Leave_action.APPROVED), Map.entry("approved", Leave_action.APPROVED),
			Map.entry("reject", Leave_action.REJECTED), Map.entry("rejected", Leave_action.REJECTED),
			Map.entry("cancel", Leave_action.CANCELLED), Map.entry("cancelled", Leave_action.CANCELLED),
			Map.entry("auto_reject", Leave_action.AUTO_REJECTED),
			Map.entry("auto_rejected", Leave_action.AUTO_REJECTED));

	private static String getSupportedInputs() {
		return String.join(", ", new TreeSet<>(actionMap.keySet()));
	}

	public static Leave_action leaveActionParse(String input) throws InvalidLeaveActionTypeException {
		if (input == null || input.isBlank()) {
			throw new InvalidLeaveActionTypeException(
					"Leave action is missing or blank. Supported values: " + getSupportedInputs(), null);
		}

		Leave_action action = actionMap.get(input.trim().toLowerCase());
		if (action == null) {
			throw new InvalidLeaveActionTypeException(
					"Unsupported leave action: '" + input + "'. Supported values: " + getSupportedInputs(), null);
		}

		return action;
	}

	public static boolean leaveApprovalValidator(JsonNode node)
			throws EmptyBodyForLeaveApprovalActionException, UnidentifiedParameterException, LeaveReasonEmptyException,
			InvalidLeaveActionTypeException, InvalidApproverIdException, InvalidLeaveIdException {

		if (node == null || node.isEmpty())
			throw new EmptyBodyForLeaveApprovalActionException("the body for leave approval request is empty or null",
					null);

		final List<String> allowedFields = List.of("leave_id", "approver_id", "action", "comments");

		Iterator<String> fields = node.fieldNames();
		while (fields.hasNext()) {
			String field = fields.next();
			if (!allowedFields.contains(field)) {
				throw new UnidentifiedParameterException("Unexpected field: " + field, null);
			}
		}

		final List<Leave_action> leaveActionListToCheckComments = List.of(Leave_action.REJECTED);
		Leave_action action;

		// Validate leave_id
		if (!node.has("leave_id") || !node.get("leave_id").canConvertToInt()) {
			throw new InvalidLeaveIdException("'leave_id' field is missing or invalid", null);
		}

		// Validate approver_id
		if (!node.has("approver_id") || !node.get("approver_id").canConvertToInt()) {
			throw new InvalidApproverIdException("'approver_id' field is missing or invalid", null);
		}

		// Validate action
		if (!node.has("action")) {
			throw new InvalidLeaveActionTypeException("'action' field is missing", null);
		}
		try {
			String actionStr = node.get("action").asText().toUpperCase();
			action = leaveActionParse(actionStr);
		} catch (IllegalArgumentException e) {
			throw new InvalidLeaveActionTypeException("'action' field is invalid. Expected 'approved','rejected'",
					null);
		}

		// Validate comments
		if (leaveActionListToCheckComments.contains(action)) {
			if (!node.has("comments") || node.get("comments").asText().trim().isEmpty()) {
				throw new LeaveReasonEmptyException("'comments' is required for leave_action: " + action, null);
			}
		}

		return true;
	}

}
