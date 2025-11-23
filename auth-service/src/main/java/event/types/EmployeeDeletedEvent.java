package event.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDeletedEvent {

	@JsonProperty("employee_id")
	private int employee_id;
	
	public EmployeeDeletedEvent() {}
	
	public EmployeeDeletedEvent(int employee_id) {
		this.employee_id = employee_id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
}
