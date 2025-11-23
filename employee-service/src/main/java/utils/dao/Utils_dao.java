package utils.dao;

import java.util.Map;
import java.util.Set;

import dto.DTO_department_query_params;
import exceptions.exception.InvalidOrderException;
import exceptions.exception.InvalidSortByException;

public class Utils_dao {

	public static boolean validateSortByAndOrder(DTO_department_query_params query_params)
			throws InvalidOrderException, InvalidSortByException {

		Set<String> orderSet = Set.of("asc", "desc");
		Set<String> sortBySet = Set.of("emp_name", "emp_id", "emp_department_id");

		if (!orderSet.contains(query_params.getOrder().toLowerCase()))
			throw new InvalidOrderException("'order' query parameter is invalid", null);

		if (!sortBySet.contains(query_params.getSortBy().toLowerCase()))
			throw new InvalidSortByException("'sortby' query parameter is invalid", null);

		return true;
	}

	public static String sqlBuilder(Map<String, Object> filters) {

		StringBuilder sql = new StringBuilder("SELECT * FROM employees WHERE 1=1 ");

		if (filters.containsKey("department")) {
			sql.append("AND LOWER(emp_department_id) = LOWER(?) ");
		}
		if (filters.containsKey("email")) {
			sql.append("AND LOWER(emp_email) = LOWER(?) ");
		}
		if (filters.containsKey("sortby")) {
			sql.append("ORDER BY ").append(filters.get("sortby")).append(" ");
			if (filters.containsKey("order")) {
				sql.append(filters.get("order")).append(" ");
			} else {
				sql.append("ASC "); // default
			}
		}
		if (filters.containsKey("limit")) {
			sql.append("LIMIT ? ");
		}
		if (filters.containsKey("page")) {
			sql.append("OFFSET ? ");
		}

		String finalQuery = sql.toString();
		if (finalQuery.contains("LIMIT") && !finalQuery.contains("ORDER BY")) {
			throw new IllegalStateException("LIMIT/OFFSET cannot be used without ORDER BY");
		}

		return finalQuery;
	}

}