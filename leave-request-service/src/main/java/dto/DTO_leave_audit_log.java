package dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Leave_action;

public class DTO_leave_audit_log {

	@JsonProperty("audit_id")
	private int audit_id;
	
	@JsonProperty("leave_id")
	private int leave_id;

	@JsonProperty("action")
	private Leave_action action;

	// employee_id OR manager_id
	@JsonProperty("performed_by")
	private int performed_by;

	@JsonProperty("comments")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String comments;

	@JsonProperty("performed_on")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Timestamp performed_on;
	
	@JsonProperty("leave_days")
	private Double leave_days;
	
	@JsonProperty("approver_comments")
	private String approver_comments;

	public DTO_leave_audit_log() {
	}

	public DTO_leave_audit_log(int audit_id, int leave_id, Leave_action action, int performed_by, String comments,
			Timestamp performed_on, Double leave_days, String approver_comments) {
		this.leave_id = leave_id;
		this.action = action;
		this.performed_by = performed_by;
		this.comments = comments;
		this.performed_on = performed_on;
		this.leave_days = leave_days;
		this.audit_id = audit_id;
		this.approver_comments = approver_comments;
	}

	public int getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(int leave_id) {
		this.leave_id = leave_id;
	}

	public Leave_action getAction() {
		return action;
	}

	public void setAction(Leave_action action) {
		this.action = action;
	}

	public int getPerformed_by() {
		return performed_by;
	}

	public void setPerformed_by(int performed_by) {
		this.performed_by = performed_by;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getPerformed_on() {
		return performed_on;
	}

	public void setPerformed_on(Timestamp performed_on) {
		this.performed_on = performed_on;
	}

	public double getLeave_days() {
		return leave_days;
	}

	public void setLeave_days(Double leave_days) {
		this.leave_days = leave_days;
	}

	public int getAudit_id() {
		return audit_id;
	}

	public void setAudit_id(int audit_id) {
		this.audit_id = audit_id;
	}

	public String getApprover_comments() {
		return approver_comments;
	}

	public void setApprover_comments(String approver_comments) {
		this.approver_comments = approver_comments;
	}
	
	
}
