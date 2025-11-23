package dto;

public class DTO_employee_delete_result {

	private final int employee_id;
	private final boolean eventFailed;

	public DTO_employee_delete_result(int employee_id, boolean eventFailed) {
		super();
		this.employee_id = employee_id;
		this.eventFailed = eventFailed;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public boolean isEventFailed() {
		return eventFailed;
	}
}
