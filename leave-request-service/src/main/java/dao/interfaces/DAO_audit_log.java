package dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dto.DTO_leave_audit_log;
import exceptions.exception.AuditLogUpdateFailedForApprovalException;
import exceptions.exception.DataAccessException;
import exceptions.exception.LeaveAuditLogNotFoundException;
import exceptions.exception.LeaveAuditLogTableEmptyException;

public interface DAO_audit_log {

	public int postAuditLog(DTO_leave_audit_log audit_log) throws SQLException;

	public List<DTO_leave_audit_log> getAuditLogForLeaveId(List<Integer> leave_ids) throws DataAccessException;

	public DTO_leave_audit_log getAuditLogForLeaveId(int leave_id)
			throws LeaveAuditLogNotFoundException, DataAccessException;

	public List<DTO_leave_audit_log> getAllAuditLog() throws LeaveAuditLogTableEmptyException, DataAccessException;

	public int updateAuditLog(Connection conn, DTO_leave_audit_log audit_log)
			throws SQLException, AuditLogUpdateFailedForApprovalException;

}
