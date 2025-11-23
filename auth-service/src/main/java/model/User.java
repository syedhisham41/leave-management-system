package model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Auth_role;

public class User {

	@JsonProperty("emp_id")
	private int emp_id;

	@JsonProperty("email")
	private String email;

	@JsonProperty("password_hash")
	private String password_hash;

	@JsonProperty("role")
	private Auth_role role;

	@JsonProperty("is_active")
	private boolean is_active;

	@JsonProperty("last_login_at")
	private Timestamp last_login_at;

	@JsonProperty("created_at")
	private Timestamp created_at;

	@JsonProperty("updated_at")
	private Timestamp updated_at;

	@JsonProperty("salt")
	private String salt;
	
	public User() {}

	public User(int emp_id, String email, String password_hash, Auth_role role, boolean is_active,
			Timestamp last_login_at, Timestamp created_at, Timestamp updated_at, String salt) {
		this.emp_id = emp_id;
		this.email = email;
		this.password_hash = password_hash;
		this.role = role;
		this.is_active = is_active;
		this.last_login_at = last_login_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.salt = salt;
	}
	
	

	public User(int emp_id, String email, String password_hash, String salt) {
		this.emp_id = emp_id;
		this.email = email;
		this.password_hash = password_hash;
		this.salt = salt;
	}



	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}

	public Auth_role getRole() {
		return role;
	}

	public void setRole(Auth_role role) {
		this.role = role;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public Timestamp getLast_login_at() {
		return last_login_at;
	}

	public void setLast_login_at(Timestamp last_login_at) {
		this.last_login_at = last_login_at;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
