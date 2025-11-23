package constants.enums;

public enum Error_code {

		INVALID_REQUEST(400, "Request is missing required parameters"),
		INVALID_INPUT_FORMAT(400, "Invalid input format"), 
		UNSUPPORTED_ID(400, "'Id' is null or invalid"),
		INVALID_METHOD(405, "HTTP method not supported"), 
		INTERNAL_ERROR(500, "Something went wrong on the server"),
		DB_CONNECTION_FAILED(503, "Unable to connect to database"),
		MISSING_REQUEST_BODY(400, "Request body is missing"),
		UNHANDLED_EXCEPTION(500, "Unhandled Exception"), 
		INVALID_NUMBER_FORMAT(400, "Invalid number format"),
		JSON_PARSE_ERROR(400, "Json parse exception"), 
		JSON_MAPPING_ERROR(400, "Json mapping exception"), 
		JSON_PROCESSING_ERROR(400, "Json processing exception"), 
		LEAVE_REQUEST_FAILED(400, "Leave request failed"),
		LEAVE_REQUEST_NOT_FOUND(404, "Leave request not found"),
		LEAVE_BALANCE_REQUEST_FAILED(400, "Leave balance request failed"),
		LEAVE_AUDIT_LOG_REQUEST_FAILED(400, "Leave Audit Log request failed"),
		INVALID_EMPLOYEE_ID(400, "Invalid employee id"),
		INVALID_LEAVE_ID(400, "Invalid leave id"),
		EMPLOYEE_NOT_FOUND(404, "Employee not found"),
		INVALID_DATE(400, "Invalid date format"),
		INVALID_LEAVE_TYPE(400, "Invalid leave type"),
		INVALID_HALF_DAY_TYPE(400, "Invalid Half day type"),
		REASON_IS_MISSING(400, "Reason is missing "),
		BAD_REQUEST(400, "Bad request"), 
		OVERLAPPING_LEAVE_REQUEST(400, "Leave request is overlapping"), 
		HALF_DAY_UNSUPPORTED(400, "HalfDay is unsupported"), 
		INSUFFICIENT_LEAVE_BALANCE(400, "Insufficient leave balance"),
		INVALID_AUDIT_ACTION(400, "Invalid Audit action type"),
		INVALID_AUDIT_LEAVE_DAYS(400, "Invalid leave days"),
		LEAVE_AUDIT_COMMENTS_NOT_FOUND(400, "Leave Audit comments not found"), 
		INVALID_LEAVE_AUDIT_MANAGER_FLAG(400, "Invalid audit manager parameter"), 
		MANAGER_NOT_FOUND(404, "Manager not found"), 
		LEAVE_ID_MISSING(400, "Leave Id is missing"), 
		EMPLOYEE_ID_MISSING(400, "Employee Id is missing"), 
		MANAGER_ID_MISSING(400, "Manager Id is missing"), 
		LEAVE_ID_DOESNT_EXIST(404, "Leave Id doesnt exist"), 
		UNSUPPORTED_PARAMETER(400, "Unsupported parameter"), 
		AUDIT_LOG_NOT_FOUND(404, "Audit log not found"), 
		INVALID_LEAVE_STATUS(400, "Invalid leave status"),
//		APPROVER_IS_MANAGER(200, "Approver is a manager"),
		APPROVER_IS_NOT_MANAGER(400, "Approver is not a manager"),
		UNKNOWN_MANAGER_FOR_EMPLOYEE(400, "Approver is not manager of employee"), 
		LEAVE_REQUEST_APPROVAL_FAILED(400, "Leave request approval failed"), 
		LEAVE_BALANCE_UPDATE_FAILED(400, "Leave balance update failed"), 
		INVALID_AUDIT_ID(400, "Invalid Audit Id"), 
		DATA_NOT_FOUND_FOR_LEAVE_APPROVAL_ACTION(400, "empty data for leave approval action"), 
		INVALID_LEAVE_ACTION(400, "Invalid Leave action type"), 
		INVALID_APPROVER_ID(400, "Invalid approver id"), 
		LEAVE_APPROVAL_COMMENTS_NOT_FOUND(400, "leave approval comments not found"),
		INVALID_SALT_KEY(400, "Error in salt key generated"),
		HASH_FUNCTION_ERROR(400, "Error in hash function generation"), 
		PASSWORD_LENGTH_ERROR(400, "Insufficient password length"), 
		INVALID_EMAIL(400, "Invalid email Id"), 
		INVALID_PASSWORD(400, "Invalid Password"), 
		USER_NOT_FOUND(500, "User not found in the DB"),
		LAST_LOGIN_UPDATE_FAILED(500,"Last login update failed"), 
		USER_RECORD_DELETE_FAILED(500, "User record delete operation failed");

		private final int http_code;

		private final String message;

		private Error_code(int http_code, String message) {
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
