package dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Auth_role;

public class DTO_user_login_response {

	@JsonProperty("emp_id")
	private Integer emp_id;

	@JsonProperty("email")
	private String email;

	@JsonProperty("role")
	private Auth_role role;

	@JsonProperty("token")
	private String token;

	@JsonProperty("issued_at")
	private Timestamp issued_at;

	@JsonProperty("expire_at")
	private Timestamp expire_at;

	public DTO_user_login_response() {
	}

	public DTO_user_login_response(Integer emp_id, String email, Auth_role role, String token, Timestamp issued_at,
			Timestamp expire_at) {
		this.emp_id = emp_id;
		this.email = email;
		this.role = role;
		this.token = token;
		this.issued_at = issued_at;
		this.expire_at = expire_at;
	}

	public Integer getEmp_id() {
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getIssued_at() {
		return issued_at;
	}

	public void setIssued_at(Timestamp issued_at) {
		this.issued_at = issued_at;
	}

	public Timestamp getExpire_at() {
		return expire_at;
	}

	public void setExpire_at(Timestamp expire_at) {
		this.expire_at = expire_at;
	}

}
