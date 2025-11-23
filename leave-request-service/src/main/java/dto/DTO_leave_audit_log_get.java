package dto;

public class DTO_leave_audit_log_get {

	private int employee_id;

	private boolean isManager;

	public DTO_leave_audit_log_get() {
	}

	public DTO_leave_audit_log_get(int employee_id, boolean isManager) {
		this.employee_id = employee_id;
		this.isManager = isManager;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

}
