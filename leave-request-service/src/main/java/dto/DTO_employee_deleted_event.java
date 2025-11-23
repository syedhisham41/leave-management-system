package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTO_employee_deleted_event {

	@JsonProperty("employee_id")
	private int employee_id;

	public DTO_employee_deleted_event() {
	}

	public DTO_employee_deleted_event(int employee_id) {
		this.employee_id = employee_id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
}
