package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import constants.enums.Leave_types;
import dao.interfaces.DAO_leave_balance;
import dto.DTO_leave_balance;
import dto.DTO_leave_request_get;
import exceptions.exception.DataAccessException;
import exceptions.exception.EmployeeNotFoundException;
import exceptions.exception.LeaveBalanceNotFoundException;
import exceptions.exception.LeaveBalanceUpdateFailedException;
import utils.service.proxy.Utils_employee_service_proxy;

public class Service_leave_balance {

	public final DAO_leave_balance leave_balance;

	public Service_leave_balance(DAO_leave_balance leave_balance) {
		this.leave_balance = leave_balance;
	}

	public int createLeaveBalance(int employee_id, boolean skipValidation)
			throws DataAccessException, EmployeeNotFoundException {

		if (!skipValidation) {
			Utils_employee_service_proxy.isEmployeeValid(employee_id);
		}

		try {
			return this.leave_balance.createLeaveBalance(employee_id);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), null);
		}
	}

	public double getLeaveBalance(int employee_id, Leave_types leave_type, boolean skipValidation)
			throws DataAccessException, EmployeeNotFoundException {

		if (!skipValidation) {
			Utils_employee_service_proxy.isEmployeeValid(employee_id);
		}

		try {
			return this.leave_balance.getLeavebalance(employee_id, leave_type);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), null);
		}
	}

	public List<DTO_leave_balance> getAllLeaveBalanceForEmployee(int employee_id, boolean skipValidation)
			throws EmployeeNotFoundException, DataAccessException {

		System.out.println("employee id in servicelayer :" + employee_id);
		if (!skipValidation) {
			Utils_employee_service_proxy.isEmployeeValid(employee_id);
		}

		try {
			return this.leave_balance.getLeavebalanceForEmployee(employee_id);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), null);
		}
	}

	public int deleteLeaveBalance(int employee_id, boolean skipValidation)
			throws EmployeeNotFoundException, DataAccessException, LeaveBalanceNotFoundException {

		if (!skipValidation) {
			Utils_employee_service_proxy.isEmployeeValid(employee_id);
		}

		try {
			return this.leave_balance.deleteLeaveBalance(employee_id);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), null);
		}
	}

	// connection is passed here as part of transaction
	public int updateLeaveBalanceByApproval(Connection conn, DTO_leave_request_get leave_request, double updatedBalance,
			boolean skipValidation) throws EmployeeNotFoundException, DataAccessException,
			LeaveBalanceNotFoundException, LeaveBalanceUpdateFailedException {

		if (!skipValidation) {
			Utils_employee_service_proxy.isEmployeeValid(leave_request.getEmployee_id());
		}

		try {
			return this.leave_balance.updateLeaveBalance(conn, leave_request.getEmployee_id(),
					leave_request.getLeave_type(), updatedBalance);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}
}
