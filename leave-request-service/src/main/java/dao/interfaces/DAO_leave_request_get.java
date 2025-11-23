package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import dto.DTO_leave_request_get;
import exceptions.exception.DataAccessException;
import exceptions.exception.LeaveRequestNotFoundException;

public interface DAO_leave_request_get {

	public List<DTO_leave_request_get> getLeaveRequestByEmployeeId(int employee_id)
			throws SQLException, DataAccessException;

	public List<DTO_leave_request_get> getLeaveRequestByEmployeeId(List<Integer> employee_id_list)
			throws SQLException, DataAccessException;

	public DTO_leave_request_get getLeaveRequestByLeaveId(int leave_id)
			throws SQLException, DataAccessException, LeaveRequestNotFoundException;

}
