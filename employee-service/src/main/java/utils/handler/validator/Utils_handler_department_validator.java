package utils.handler.validator;

import java.util.Map;

import exceptions.exception.DepartmentIdMissingException;
import exceptions.exception.UnIdentifiedParameterException;

public class Utils_handler_department_validator {

	public static String getDepartmentIdFromQueryAfterValidation(Map<String, String> params)
			throws DepartmentIdMissingException, UnIdentifiedParameterException {

		String[] supportedIdKeys = { "department_id", "id" };

		for (String key : supportedIdKeys) {
			if (params.containsKey(key)) {
				String department_id = params.get(key).toUpperCase();
				if (department_id != null && !department_id.isBlank())
					return department_id;
				throw new DepartmentIdMissingException(
						"Missing or blank value for required query parameter: 'department_id' or 'id'", null);
			}
		}
		throw new UnIdentifiedParameterException("Unrecognized query parameter. Expected 'department_id' or 'id'",
				null);
	}
}
