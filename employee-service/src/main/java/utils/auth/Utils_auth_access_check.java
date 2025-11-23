package utils.auth;

import java.util.Arrays;

import com.auth0.jwt.interfaces.DecodedJWT;
import constants.enums.Auth_role;

public class Utils_auth_access_check {

	public static boolean hasAdminAccess(DecodedJWT jwt) {
		return hasRole(jwt, Auth_role.ADMIN);
	}

	public static boolean hasManagerOrAdminAccess(DecodedJWT jwt) {
		return hasRole(jwt, Auth_role.MANAGER, Auth_role.ADMIN);
	}

	public static boolean hasRole(DecodedJWT jwt, Auth_role... roles) {
		String role = jwt.getClaim("role").asString();
		return Arrays.stream(roles).anyMatch(r -> r.name().equalsIgnoreCase(role));
	}
}
