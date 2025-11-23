package model;

import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthResult {
	
	private boolean isInternal;
	private DecodedJWT jwt;
	
	public AuthResult(boolean isInternal, DecodedJWT jwt) {
		this.isInternal = isInternal;
		this.jwt = jwt;
	}

	public boolean isInternal() {
		return isInternal;
	}

	public void setInternal(boolean isInternal) {
		this.isInternal = isInternal;
	}

	public DecodedJWT getJwt() {
		return jwt;
	}

	public void setJwt(DecodedJWT jwt) {
		this.jwt = jwt;
	}
	
}
