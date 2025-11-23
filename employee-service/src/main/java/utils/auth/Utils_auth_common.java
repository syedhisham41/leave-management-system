package utils.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import config.PropertyConfig;
import exceptions.exception.UnauthorizedException;
import model.AuthResult;

public class Utils_auth_common {

	public static AuthResult authenticateRequest(HttpExchange exchange) throws UnauthorizedException {
		Headers headers = exchange.getRequestHeaders();
		String serviceSecret = PropertyConfig.get("SERVICE_SECRET");
		String jwtSecret = PropertyConfig.get("JWT_SECRET");

		String internalToken = headers.getFirst("X-Service-Auth");
		if (internalToken != null) {
			if (serviceSecret.equals(internalToken)) {
				return new AuthResult(true, null);
			} else {
				throw new UnauthorizedException("Invalid service secret", null);
			}
		}

		String authHeader = headers.getFirst("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new UnauthorizedException("Missing Authorization header", null);
		}

		String token = authHeader.substring("Bearer ".length()).trim();
		DecodedJWT jwt = JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(token);
		return new AuthResult(false, jwt);
	}
}
