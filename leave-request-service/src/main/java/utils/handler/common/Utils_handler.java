package utils.handler.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import com.sun.net.httpserver.HttpExchange;

import constants.enums.Error_code;
import constants.enums.Half_day_types;
import constants.enums.Leave_types;
import constants.enums.Success_code;
import dto.DTO_api_response;
import dto.DTO_employee_created_event;
import dto.DTO_employee_id_and_leave_type;
import dto.DTO_leave_audit_log_get;
import dto.DTO_leave_request;
import exceptions.exception.InvalidAuditManagerFlagException;
import exceptions.exception.InvalidDateException;
import exceptions.exception.InvalidEmployeeIdException;
import exceptions.exception.InvalidHalfDayTypeException;
import exceptions.exception.InvalidLeaveTypeException;
import exceptions.exception.LeaveReasonEmptyException;
import exceptions.exception.UnidentifiedParameterException;
import exceptions.exception.UnsupportedQueryParameterException;
import utils.http.Utils_http;

public class Utils_handler {

	public static DTO_leave_request parseQueryParamForLeaveRequest(String query) throws InvalidEmployeeIdException,
			UnsupportedEncodingException, InvalidDateException, InvalidLeaveTypeException, InvalidHalfDayTypeException,
			LeaveReasonEmptyException, UnidentifiedParameterException {

		if (query == null || query.isEmpty())
			return new DTO_leave_request();

		DTO_leave_request query_param_leave_request_dto = new DTO_leave_request();

		for (String pair : query.split("&")) {
			String[] kv = pair.split("=");
			switch (kv[0]) {

			case "employee_id":
				try {
					if (kv.length >= 2)
						query_param_leave_request_dto.setEmployee_id(
								Integer.parseInt(java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name())));
					else
						throw new InvalidEmployeeIdException("'employee_id' field is invalid or null", null);
				} catch (NumberFormatException e) {
					throw new InvalidEmployeeIdException("'employee_id' field is invalid format", null);
				}
				break;

			case "start_date":
				if (kv.length >= 2)
					query_param_leave_request_dto.setStart_date(LocalDate.parse(kv[1]));
				else
					throw new InvalidDateException("'start_date' field is invalid or null", null);
				break;

			case "end_date":
				if (kv.length >= 2)
					query_param_leave_request_dto.setEnd_date(LocalDate.parse(kv[1]));
				else
					throw new InvalidDateException("'start_date' field is invalid or null", null);
				break;

			case "leave_type":
				if (kv.length >= 2) {
					for (Leave_types lt : Leave_types.values()) {
						if (lt.toString().equalsIgnoreCase(kv[1])) {
							query_param_leave_request_dto.setLeave_type(lt);
							break;
						}
					}
				} else
					throw new InvalidLeaveTypeException("'leave_type' field is invalid or null", null);
				break;

			case "half_day_type":
				if (kv.length >= 2) {
					for (Half_day_types ht : Half_day_types.values()) {
						if (ht.toString().equalsIgnoreCase(kv[1])) {
							query_param_leave_request_dto.setHalf_day_type(ht);
							break;
						}
					}
				} else
					throw new InvalidHalfDayTypeException("'half_day_type' field is invalid or null", null);

				break;

			case "reason":
				if (kv.length >= 2) {
					query_param_leave_request_dto
							.setReason(java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name()));

				} else
					throw new LeaveReasonEmptyException("'reason' field is null or empty", null);
				break;

			default:
				throw new UnidentifiedParameterException("Parameter is not identified :" + kv[0], null);
			}
		}
		return query_param_leave_request_dto;
	}

	public static DTO_employee_created_event parseQueryParamForEmployeeId(String query) {

		if (query == null || query.isEmpty())
			return new DTO_employee_created_event();

		DTO_employee_created_event employee_id = new DTO_employee_created_event();
		for (String pair : query.split("&")) {
			String[] kv = pair.split("=");
			if (kv.length >= 2 && kv[0].equalsIgnoreCase("employee_id")) {
				employee_id.setEmployee_id(Integer.parseInt(kv[1]));
			}
		}
		return employee_id;
	}

	public static DTO_employee_id_and_leave_type parseQueryParamForGetLeaveBalance(String query)
			throws InvalidEmployeeIdException, InvalidLeaveTypeException, UnsupportedEncodingException,
			UnidentifiedParameterException {

		if (query == null || query.isEmpty())
			return new DTO_employee_id_and_leave_type();

		DTO_employee_id_and_leave_type employee_id_and_leave_type = new DTO_employee_id_and_leave_type();
		for (String pair : query.split("&")) {
			String[] kv = pair.split("=");
			switch (kv[0]) {

			case "employee_id":
				try {
					if (kv.length >= 2)
						employee_id_and_leave_type.setEmployee_id(
								Integer.parseInt(java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name())));
					else
						throw new InvalidEmployeeIdException("'employee_id' field is invalid or null", null);
				} catch (NumberFormatException e) {
					throw new InvalidEmployeeIdException("'employee_id' field is invalid format", null);
				}
				break;

			case "leave_type":
				if (kv.length >= 2) {
					for (Leave_types lt : Leave_types.values()) {
						if (lt.toString().equalsIgnoreCase(kv[1])) {
							employee_id_and_leave_type.setLeave_type(lt);
							break;
						}
					}
				} else
					throw new InvalidLeaveTypeException("'leave_type' field is invalid or null", null);
				break;

			default:
				throw new UnidentifiedParameterException("Parameter is not identified :" + kv[0], null);
			}
		}
		return employee_id_and_leave_type;
	}

	public static DTO_leave_audit_log_get parseQueryParamForLeaveAuditLogGet(String query)
			throws InvalidEmployeeIdException, UnsupportedEncodingException, InvalidAuditManagerFlagException,
			UnidentifiedParameterException {

		DTO_leave_audit_log_get audit_log = new DTO_leave_audit_log_get();

		for (String pair : query.split("&")) {
			String[] kv = pair.split("=");
			switch (kv[0]) {

			case "employee_id":
				try {
					if (kv.length >= 2)
						audit_log.setEmployee_id(
								Integer.parseInt(java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name())));
					else
						throw new InvalidEmployeeIdException("'employee_id' field is invalid or null", null);
				} catch (NumberFormatException e) {
					throw new InvalidEmployeeIdException("'employee_id' field is invalid format", null);
				}
				break;

			case "manager":
				if (kv.length >= 2) {
					audit_log.setManager(Boolean.getBoolean(kv[1]));

				} else
					throw new InvalidAuditManagerFlagException("'manager' field is invalid or null", null);
				break;

			default:
				throw new UnidentifiedParameterException("Parameter is not identified :" + kv[0], null);
			}
		}
		return audit_log;

	}

	public static Map<String, String> parseCommonQueryParams(String query) {
		Map<String, String> result = new HashMap<>();
		if (query == null || query.isEmpty())
			return result;

		String[] pairs = query.split("&");
		for (String pair : pairs) {
			String[] kv = pair.split("=", 2); // limit to 2 to allow '=' in value
			if (kv.length == 2) {
				result.put(kv[0].toLowerCase(), kv[1]);
			}
		}
		return result;
	}

	public static void validateAllowedParams(Map<String, String> params, String[]... allowedKeyGroups)
			throws UnsupportedQueryParameterException {

		Set<String> allowed = new HashSet<>();
		for (String[] group : allowedKeyGroups) {
			allowed.addAll(Arrays.asList(group));
		}

		for (String key : params.keySet()) {
			if (!allowed.contains(key)) {
				throw new UnsupportedQueryParameterException("Unsupported query parameter: " + key, null);
			}
		}
	}

	public static <T> void APIResponse(HttpExchange exchange, Error_code error_code) throws IOException {

		DTO_api_response<T> response = new DTO_api_response<T>();

		response.setHttp_code(error_code.getHttp_code());
		response.setCode(error_code.toString());
		response.setMessage(error_code.getMessage());

		Utils_http.sendJsonResponse(exchange, response, error_code.getHttp_code());
	}

	public static <T> void APIResponse(HttpExchange exchange, Success_code success_code, T data) throws IOException {

		DTO_api_response<T> response = new DTO_api_response<T>();

		response.setHttp_code(success_code.getHttp_code());
		response.setCode(success_code.toString());
		response.setMessage(success_code.getMessage());
		response.setData(data);

		Utils_http.sendJsonResponse(exchange, response, success_code.getHttp_code());
	}

	public static <T> void APIResponse(HttpExchange exchange, DTO_api_response<T> response) throws IOException {
		Utils_http.sendJsonResponse(exchange, response, response.getHttp_code());
	}
}
