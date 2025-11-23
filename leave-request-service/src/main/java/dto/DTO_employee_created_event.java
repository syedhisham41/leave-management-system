package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTO_employee_created_event {

	@JsonProperty("employee_id")
	private int employee_id;
	
	@JsonProperty("emp_department_id")
	private String departmentId;
	
	public DTO_employee_created_event(){}
	
	public DTO_employee_created_event(int employee_id, String departmentId) {
		this.employee_id = employee_id;
		this.departmentId = departmentId;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	
}
