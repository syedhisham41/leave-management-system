package utils.handler.validator;

import java.util.Map;

import exceptions.exception.ManagerIdMissingException;
import exceptions.exception.UnIdentifiedParameterException;

public class Utils_handler_manager_validator {

	public static String[] supportedManagerIdKeys = { "manager_id", "managerid", "manager" };

	public static String getManagerFromQueryAfterValidation(Map<String, String> params)
			throws UnIdentifiedParameterException, ManagerIdMissingException {

		for (String key : supportedManagerIdKeys) {
			if (params.containsKey(key)) {
				String manager_id = params.get(key);
				if (manager_id != null && !manager_id.isBlank())
					return manager_id;
				throw new ManagerIdMissingException(
						"Missing or blank value for required query parameter: 'manager_id' or 'manager'", null);
			}
		}
		throw new UnIdentifiedParameterException("Unrecognized query parameter. Expected 'manager_id' or 'manager'",
				null);
	}
}
