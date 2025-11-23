package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Employee_role;

public class DTO_manager_check {

	@JsonProperty("isManager")
	private boolean isManager;

	@JsonProperty("department_id")
	private String department_id;

	@JsonProperty("employee_id")
	private int employee_id;

	@JsonProperty("role")
	private Employee_role role;

	public DTO_manager_check() {
	}

	public DTO_manager_check(boolean isManager, String department_id, int employee_id, Employee_role role) {
		this.isManager = isManager;
		this.department_id = department_id;
		this.employee_id = employee_id;
		this.role = role;
	}

	public boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public Employee_role getRole() {
		return role;
	}

	public void setRole(Employee_role role) {
		this.role = role;
	}

}
