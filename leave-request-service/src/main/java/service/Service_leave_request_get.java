package service;

import java.sql.SQLException;
import java.util.List;

import dao.interfaces.DAO_leave_request_get;
import dto.DTO_leave_request_get;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.InvalidLeaveIdException;
import exceptions.exception.LeaveRequestNotFoundException;
import exceptions.exception.ManagerNotFoundException;
import utils.service.Utils_service;
import utils.service.proxy.Utils_employee_service_proxy;

public class Service_leave_request_get {

	public final DAO_leave_request_get leave_request_get_dao;

	public Service_leave_request_get(DAO_leave_request_get leave_request_get_dao) {
		this.leave_request_get_dao = leave_request_get_dao;
	}

	public List<DTO_leave_request_get> getLeaveRequestByEmployeeId(int employee_id)
			throws SQLException, DataAccessException, EmployeeNotFoundException, LeaveRequestNotFoundException {

		boolean status = Utils_employee_service_proxy.isEmployeeValid(employee_id);
		System.out.println("status of employee search :" + status);

		List<DTO_leave_request_get> leave_request_list = this.leave_request_get_dao
				.getLeaveRequestByEmployeeId(employee_id);

		if (leave_request_list == null || leave_request_list.isEmpty())
			throw new LeaveRequestNotFoundException("Leave request not found for employee_id :" + employee_id, null);

		for (DTO_leave_request_get each : leave_request_list) {
			each.setLeave_days(Utils_service.getNumberofLeaveDays(each));
		}
		return leave_request_list;
	}

	public DTO_leave_request_get getLeaveRequestByLeaveId(int leave_id) throws SQLException, DataAccessException,
			EmployeeNotFoundException, InvalidLeaveIdException, LeaveRequestNotFoundException {

		if (leave_id <= 0)
			throw new InvalidLeaveIdException("leave_id is invalid", null);

		DTO_leave_request_get leave_request;
		leave_request = this.leave_request_get_dao.getLeaveRequestByLeaveId(leave_id);
		leave_request.setLeave_days(Utils_service.getNumberofLeaveDays(leave_request));

		return leave_request;
	}

	public List<DTO_leave_request_get> getLeaveRequestByManagerId(int manager_id) throws SQLException,
			DataAccessException, EmployeeNotFoundException, ManagerNotFoundException, LeaveRequestNotFoundException {

		// verify if managerId is a manager and employee
		if (!Utils_employee_service_proxy.isManagerValid(manager_id))
			throw new ManagerNotFoundException("employee is not a manager", null);

		// get all the employees using manager
		List<Integer> id_list = Utils_employee_service_proxy.getIdOfEmployeesByManagerId(manager_id);

		List<DTO_leave_request_get> leave_request_list = this.leave_request_get_dao
				.getLeaveRequestByEmployeeId(id_list);
		if (leave_request_list == null || leave_request_list.isEmpty())
			throw new LeaveRequestNotFoundException("leave requests not found for the manager :" + manager_id, null);

		for (DTO_leave_request_get leave : leave_request_list) {
			leave.setLeave_days(Utils_service.getNumberofLeaveDays(leave));
		}
		return leave_request_list;
	}

	public List<DTO_leave_request_get> getAllLeaveRequest() {
		// TODO Auto-generated method stub
		return null;
	}

}
