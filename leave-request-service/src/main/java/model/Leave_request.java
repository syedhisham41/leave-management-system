package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import constants.enums.Half_day_types;
import constants.enums.Leave_status;
import constants.enums.Leave_types;

public class Leave_request {


	private int leave_id;
	private int employee_id;
	private LocalDate start_date;
	private LocalDate end_date;
	private Half_day_types half_day_type;
	private Leave_types leave_type;
	private Leave_status status;
	private Integer approver_id;
	private LocalDateTime applied_on;
    private LocalDateTime decision_on;
	private String reason;

	public Leave_request() {
		this.half_day_type = Half_day_types.NONE;
		this.status = Leave_status.PENDING;
	}

	public int getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(int leave_id) {
		this.leave_id = leave_id;
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

	public Half_day_types getHalf_day_type() {
		return half_day_type;
	}

	public void setHalf_day_type(Half_day_types half_day_type) {
		this.half_day_type = half_day_type;
	}

	public Leave_types getLeave_type() {
		return leave_type;
	}

	public void setLeave_type(Leave_types leave_type) {
		this.leave_type = leave_type;
	}

	public Leave_status getStatus() {
		return status;
	}

	public void setStatus(Leave_status status) {
		this.status = status;
	}

	public Integer getApprover_id() {
		return approver_id;
	}

	public void setApprover_id(Integer approver_id) {
		this.approver_id = approver_id;
	}

	public LocalDateTime getApplied_on() {
		return applied_on;
	}

	public void setApplied_on(LocalDateTime appliedOn) {
		this.applied_on = appliedOn;
	}

	public LocalDateTime getDecision_on() {
		return decision_on;
	}

	public void setDecision_on(LocalDateTime decisionOn) {
		this.decision_on = decisionOn;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
