package model;

import java.sql.Timestamp;

public class Jwt {
	
	private String token;
	private Timestamp issued_at;
	private Timestamp expired_at;
	
	public Jwt(String token, Timestamp issued_at, Timestamp expired_at) {
		this.token = token;
		this.issued_at = issued_at;
		this.expired_at = expired_at;
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

	public Timestamp getExpired_at() {
		return expired_at;
	}

	public void setExpired_at(Timestamp expired_at) {
		this.expired_at = expired_at;
	}
	
	

}
