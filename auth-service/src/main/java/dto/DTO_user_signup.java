package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTO_user_signup {

	@JsonProperty("emp_id")
	private Integer emp_id;

	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	public DTO_user_signup() {}
	
	public DTO_user_signup(int emp_id, String email, String password) {
		this.emp_id = emp_id;
		this.email = email;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
