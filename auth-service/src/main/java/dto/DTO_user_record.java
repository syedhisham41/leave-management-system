package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Auth_role;

public class DTO_user_record {

	@JsonProperty("emp_id")
	private Integer emp_id;

	@JsonProperty("email")
	private String email;

	@JsonProperty("role")
	private Auth_role role;

	public DTO_user_record() {
	}

	public DTO_user_record(int emp_id, String email, Auth_role role) {
		this.emp_id = emp_id;
		this.email = email;
		this.role = role;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Auth_role getRole() {
		return role;
	}

	public void setRole(Auth_role role) {
		this.role = role;
	}

}
