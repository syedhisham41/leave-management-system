package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Leave_action;

public class DTO_leave_request_approval_action {
	
	@JsonProperty("approver_id")
	private int approver_id;
	
	@JsonProperty("leave_id")
	private int leave_id;
	
	@JsonProperty("action")
	private Leave_action action;

	@JsonProperty("comments")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String comments;

	public DTO_leave_request_approval_action() {
	}

	public DTO_leave_request_approval_action( int approver_id, int leave_id, Leave_action action, String comments) {
		this.approver_id = approver_id;
		this.leave_id = leave_id;
		this.action = action;
		this.comments = comments;
	}

	public int getApprover_id() {
		return approver_id;
	}

	public void setApprover_id(int approver_id) {
		this.approver_id = approver_id;
	}

	public Leave_action getAction() {
		return action;
	}

	public void setAction(Leave_action action) {
		this.action = action;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(int leave_id) {
		this.leave_id = leave_id;
	}
	
	

	
}