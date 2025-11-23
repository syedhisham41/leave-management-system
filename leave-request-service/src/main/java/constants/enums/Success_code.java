package constants.enums;

public enum Success_code {
		
		LEAVE_REQUEST_CREATED(201, "Leave Request created successfully"),
		LEAVE_REQUEST_DELETED(200, "Leave Request deleted successfully"),
		LEAVE_REQUEST_UPDATED(200, "Leave Request updated successfully"),
		LEAVE_BALANCE_CREATED(201, "Leave Balance created successfully"),
		LEAVE_BALANCE_DELETED(200, "Leave Balance deleted successfully"),
		LEAVE_BALANCE_UPDATED(200, "Leave Balance updated successfully"),
		LEAVE_REQUEST_LIST_EMPTY(204,"Leave Request list is empty"),
		LEAVE_REQUEST_FETCHED(200, "Leave Request fetched successfully"),
		LEAVE_BALANCE_FETCHED(200, "Leave Balance fetched successfully"),
		LEAVE_AUDIT_LOG_FETCHED(200, "Leave Audit Log fetched successfully"),
		LEAVE_AUDIT_LOG_UPDATED(200, "Leave Audit Log updated successfully"),
		LEAVE_AUDIT_LOG_DELETED(200, "Leave Audit Log deleted successfully"),
		EMPLOYEE_LIST_EMPTY(204,"Employee list is empty"),
		EMPLOYEE_FETCHED(200, "Employee fetched successfully"),
		DEBUG_INFO(200,"Debug Info fetched successfully"), 
		LEAVE_REQUEST_APPROVED(200,"Leave Request approved"), 
		LEAVE_REQUEST_REJECTED(200,"Leave Request rejected"),
		LEAVE_REQUEST_CANCELLED(200,"Leave Request cancelled");
		
		
		private final int http_code;

		private final String message;
		
		private Success_code(int http_code, String message) {
			this.http_code = http_code;
			this.message = message;
		}

		public int getHttp_code() {
			return http_code;
		}

		public String getMessage() {
			return message;
		}
}
