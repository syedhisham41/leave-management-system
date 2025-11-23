package event.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeCreatedEvent {

	@JsonProperty("employee_id")
	private int employee_id;
	
	@JsonProperty("emp_department_id")
	private String emp_department_id;
	
	public EmployeeCreatedEvent() {}
	
	public EmployeeCreatedEvent(int employee_id, String emp_department_id) {
		this.employee_id = employee_id;
		this.emp_department_id = emp_department_id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmp_department_id() {
		return emp_department_id;
	}

	public void setEmp_department_id(String emp_department_id) {
		this.emp_department_id = emp_department_id;
	}


	
	
	
	
}
