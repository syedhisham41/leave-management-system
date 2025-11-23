package dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Half_day_types;
import constants.enums.Leave_types;

public class DTO_leave_request_get {

	@JsonProperty("leave_id")
	private int leave_id;

	@JsonProperty("employee_id")
	private int employee_id;

	@JsonProperty("start_date")
	private LocalDate start_date;

	@JsonProperty("end_date")
	private LocalDate end_date;

	@JsonProperty("leave_type")
	private Leave_types leave_type;

	@JsonProperty("half_day_type")
	private Half_day_types half_day_type;

	@JsonProperty("status")
	private String status;

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("applied_on")
	private String applied_on;

	@JsonProperty("decision_on")
	private String decision_on;

	@JsonProperty("approver_id")
	private Integer approver_id;
	
	@JsonProperty("approver_comments")
	private String approver_comments;
	
	private double leave_days;

	public DTO_leave_request_get() {
	}

	public DTO_leave_request_get(int leave_id, int employee_id, LocalDate start_date, LocalDate end_date,
			Leave_types leave_type, Half_day_types half_day_type, String status, String reason, String applied_on,
			String decision_on, int approver_id, String approver_comments, double leave_days) {
		this.leave_id = leave_id;
		this.employee_id = employee_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.leave_type = leave_type;
		this.half_day_type = half_day_type;
		this.status = status;
		this.reason = reason;
		this.applied_on = applied_on;
		this.decision_on = decision_on;
		this.approver_id = approver_id;
		this.approver_comments = approver_comments;
		this.leave_days = leave_days;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public Leave_types getLeave_type() {
		return leave_type;
	}

	public void setLeave_type(Leave_types leave_type) {
		this.leave_type = leave_type;
	}

	public Half_day_types getHalf_day_type() {
		return half_day_type;
	}

	public void setHalf_day_type(Half_day_types half_day_type) {
		this.half_day_type = half_day_type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(int leave_id) {
		this.leave_id = leave_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplied_on() {
		return applied_on;
	}

	public void setApplied_on(String applied_on) {
		this.applied_on = applied_on;
	}

	public String getDecision_on() {
		return decision_on;
	}

	public void setDecision_on(String decision_on) {
		this.decision_on = decision_on;
	}

	public Integer getApprover_id() {
		return approver_id;
	}

	public void setApprover_id(Integer approver_id) {
		this.approver_id = approver_id;
	}

	public double getLeave_days() {
		return leave_days;
	}

	public void setLeave_days(double leave_days) {
		this.leave_days = leave_days;
	}

	public String getApprover_comments() {
		return approver_comments;
	}

	public void setApprover_comments(String approver_comments) {
		this.approver_comments = approver_comments;
	}
	
	

}
