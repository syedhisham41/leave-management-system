package constants.enums;

public class Error_code {

	public enum error_code {

		INVALID_REQUEST(400, "Request is missing required parameters"),
		INVALID_INPUT_FORMAT(400, "Invalid input format"), 
		UNSUPPORTED_SORT_FIELD(400, "Invalid sort field"),
		UNSUPPORTED_ORDER(400, "Invalid sort order (should be ASC/DESC)"),
		UNSUPPORTED_ID(400, "'Id' is null or invalid"),
		UNSUPPORTED_PAGE(400, "'Page' is null or invalid"),
		UNSUPPORTED_LIMIT(400, "'Limit' is null or invalid"),
		UNSUPPORTED_DEPARTMENT(400, "'Department' is null or invalid"),
		UNSUPPORTED_NAME(400, "'name' is null or invalid"),
		UNSUPPORTED_EMAIL(400, "'email' is null or invalid"),
		UNSUPPORTED_JOINING_DATE(400, "'joining date' is null or invalid"),
		INVALID_METHOD(405, "HTTP method not supported"), 
		EMPLOYEE_NOT_FOUND(404, "Employee not found"),
		DEPARTMENT_NOT_FOUND(404, "Department not found"), 
		MANAGER_NOT_FOUND(404, "Manager not found"), 
		UNKNOWN_DEPARTMENT(400, "Unknown Department"),
		INTERNAL_ERROR(500, "Something went wrong on the server"),
		DB_CONNECTION_FAILED(503, "Unable to connect to database"),
		EMPLOYEE_ALREADY_EXISTS(409, "Employee already exists"), 
		MISSING_REQUEST_BODY(400, "Request body is missing"),
		UNHANDLED_EXCEPTION(500, "Unhandled Exception"), 
		INVALID_NUMBER_FORMAT(400, "Invalid number format"),
		JSON_PARSE_ERROR(400, "Json parse exception"), 
		JSON_MAPPING_ERROR(400, "Json mapping exception"), 
		JSON_PROCESSING_ERROR(400, "Json processing exception"), 
		LEAVE_SERVICE_UNREACHABLE(404, "Leave Service Unreachable"), 
		DEPARTMENT_ID_MISSING(400, "Department ID is missing"), 
		INVALID_PARAMETER(400, "Invalid parameter value"), 
		UNKNOWN_PARAMETER(400, "Unknown parameter"), 
		INVALID_ENDPOINT(404, "endpoint not found"), 
		UNAUTHORIZED_ERROR(401,"Unauthorized"), 
		FORBIDDEN_ERROR(403, "Forbidden");

		private final int http_code;

		private final String message;

		error_code(int http_code, String message) {
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
}
