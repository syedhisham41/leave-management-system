package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Employee_role;

public class DTO_department_manager {

	@JsonProperty("department_id")
	private String department_id;

	@JsonProperty("manager_id")
	private int manager_id;

	@JsonProperty("role")
	private Employee_role role;

	public DTO_department_manager() {
	}

	public DTO_department_manager(String department_id, int manager_id, Employee_role role) {
		this.department_id = department_id;
		this.manager_id = manager_id;
		this.role = role;
	}

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}

	public Employee_role getRole() {
		return role;
	}

	public void setRole(Employee_role role) {
		this.role = role;
	}
}
