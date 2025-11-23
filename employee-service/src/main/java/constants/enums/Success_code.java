package constants.enums;

public class Success_code {
	
	public enum success_code{
		
		EMPLOYEE_CREATED(201, "Employee created successfully"),
	    EMPLOYEE_DELETED(200, "Employee deleted successfully"),
	    EMPLOYEE_UPDATED(200, "Employee updated successfully"),
	    DEPARTMENT_FETCHED(200, "Department fetched successfully"),
		EMPLOYEE_LIST_EMPTY(204,"Employee list is empty"),
		DEPARTMENT_LIST_EMPTY(204,"Employee list is empty"),
		EMPLOYEE_FETCHED(200, "Employee fetched successfully"),
		MANAGER_FETCHED(200, "Managers fetched successfully"),
		EMPLOYEE_IS_MANAGER(200, "Employee is a manager"),
		EMPLOYEE_IS_NOT_MANAGER(200, "Employee is not a manager"),
		DEBUG_INFO(200,"Debug Info fetched successfully"), 
		EMPLOYEE_CREATED_EVENT_FAILED(207, "Employee created but event dispatch failed"),
		EMPLOYEE_DELETED_EVENT_FAILED(207, "Employee deleted but event dispatch failed"), 
		MANAGER_LIST_EMPTY(204,"Manager list is empty") ;
		
		
		private final int http_code;

		private final String message;
		
		success_code(int http_code, String message) {
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
