package utils.auth;

import java.util.Arrays;
import java.util.List;

import com.auth0.jwt.interfaces.DecodedJWT;
import constants.enums.Auth_role;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import utils.service.proxy.Utils_employee_service_proxy;

public class Utils_auth_access_check {

	public static boolean hasAdminAccess(DecodedJWT jwt) {
		return hasRole(jwt, Auth_role.ADMIN);
	}

	public static boolean hasManagerAccess(DecodedJWT jwt) {
		return hasRole(jwt, Auth_role.MANAGER);
	}

	public static boolean hasManagerOrAdminAccess(DecodedJWT jwt) {
		return hasRole(jwt, Auth_role.MANAGER, Auth_role.ADMIN);
	}

	public static boolean hasRole(DecodedJWT jwt, Auth_role... roles) {
		String role = jwt.getClaim("role").asString();
		return Arrays.stream(roles).anyMatch(r -> r.name().equalsIgnoreCase(role));
	}
	
	public static boolean ifEmployeeHasAccess(DecodedJWT jwt, int employee_id)
	        throws DataAccessException, EmployeeNotFoundException {

	    int requesterEmpId = jwt.getClaim("emp_id").asInt();

	    // Admin gets full access
	    if (hasAdminAccess(jwt)) return true;

	    // Employee can always access their own data, regardless of roles
	    if (requesterEmpId == employee_id) return true;

	    // Manager can access employees reporting to them
	    if (hasManagerAccess(jwt)) {
	        List<Integer> reporteeIds = Utils_employee_service_proxy.getIdOfEmployeesByManagerId(requesterEmpId);
	        if (reporteeIds.contains(employee_id)) return true;
	    }

	    return false;
	}

	public static boolean ifManagerHasAccess(DecodedJWT jwt, int manager_id) {

		// if admin employee, should have access
		if (hasAdminAccess(jwt))
			return true;

		// check if the manager is trying to access leave request using his manager id
		if (hasManagerAccess(jwt)) {
			int man_id = jwt.getClaim("emp_id").asInt();
			return man_id == manager_id;
		}
		return false;
	}
}
