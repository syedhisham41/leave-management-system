package utils.handler.validator;

import java.util.Map;

import exceptions.exception.*;

public class Utils_handler_leave_audit_validator {

	public static String[] supportedManagerCheckKeys = { "manager", "ismanager" };

	public static boolean getManagerCheckFromQueryAfterValidation(Map<String, String> params)
			throws UnidentifiedParameterException, ManagerIdMissingException, InvalidManagerIdException {

		for (String key : supportedManagerCheckKeys) {
			if (params.containsKey(key)) {
				String manager = params.get(key);
				if (manager != null && !manager.isBlank())
					return Boolean.parseBoolean(manager);
				throw new ManagerIdMissingException(
						"Missing or blank value for required query parameter: 'manager' or 'ismanager'", null);
			}
			else {
				return false;
			}
		}
		throw new UnidentifiedParameterException("Unrecognized query parameter. Expected 'manager' or 'ismanager'",
				null);
	}
}
