package constants.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Leave_action {

	APPLIED, APPROVED, REJECTED, CANCELLED, AUTO_REJECTED;

	@JsonCreator
    public static Leave_action fromString(String input) {
        switch (input.toLowerCase()) {
            case "applied": return APPLIED;
            case "approved":
            case "approve": return APPROVED;
            case "rejected":
            case "reject": return REJECTED;
            case "cancelled":
            case "cancel": return CANCELLED;
            case "auto_rejected": return AUTO_REJECTED;
            default:
                throw new IllegalArgumentException("Invalid leave action: " + input);
        }
    }
}
