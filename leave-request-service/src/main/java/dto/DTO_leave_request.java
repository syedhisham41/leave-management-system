package dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Half_day_types;
import constants.enums.Leave_types;

public class DTO_leave_request {

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

	@JsonProperty("reason")
	private String reason;

	public DTO_leave_request() {
		this.half_day_type = Half_day_types.NONE;
	}

	public DTO_leave_request(int employee_id, LocalDate start_date, LocalDate end_date, Leave_types leave_type,
			Half_day_types half_day_type, String reason) {
		super();
		this.employee_id = employee_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.leave_type = leave_type;
		this.half_day_type = half_day_type;
		this.reason = reason;
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
}
