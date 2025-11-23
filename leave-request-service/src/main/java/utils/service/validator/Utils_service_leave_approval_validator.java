package utils.service.validator;

import java.util.List;

import dto.DTO_leave_request_get;
import exceptions.exception.*;
import utils.service.proxy.Utils_employee_service_proxy;

public class Utils_service_leave_approval_validator {

	public static boolean verifyIfApproverIsManagerOfEmployee(DTO_leave_request_get leave_request, int approver_id)
			throws EmployeeNotFoundException, DataAccessException {

		List<Integer> employee_list = Utils_employee_service_proxy.getIdOfEmployeesByManagerId(approver_id);

		for (int id : employee_list) {
			if (leave_request.getEmployee_id() == id)
				return true;
		}
		return false;
	}
}
