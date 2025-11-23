package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTO_user_login_request {

	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	public DTO_user_login_request() {
	}

	public DTO_user_login_request(String email, String password) {
		this.email = email;
		this.password = password;
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
