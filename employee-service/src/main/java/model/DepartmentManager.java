package model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Employee_role;

public class DepartmentManager {

	@JsonProperty("department_id")
	private String department_id;

	@JsonProperty("manager_id")
	private int manager_id;

	@JsonProperty("role")
	private Employee_role role;
	
	@JsonProperty("created_at")
	private Timestamp created_at;

	@JsonProperty("updated_at")
	private Timestamp updated_at;

	public DepartmentManager() {
	}

	public DepartmentManager(String department_id, int manager_id, Employee_role role, Timestamp created_at, Timestamp updated_at) {
		this.department_id = department_id;
		this.manager_id = manager_id;
		this.role = role;
		this.updated_at = updated_at;
		this.created_at = created_at;
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

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	
	
}
