package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import constants.enums.Leave_types;

public class DTO_leave_balance {

	@JsonProperty("employee_id")
	private int employee_id;
	
	@JsonProperty("leave_type")
	private Leave_types leave_type;
	
	@JsonProperty("balance")
	private double balance;
	
	public DTO_leave_balance(){}
	
	public DTO_leave_balance(int employee_id, Leave_types leave_type, double balance) {
		this.employee_id = employee_id;
		this.leave_type = leave_type;
		this.balance = balance;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public Leave_types getLeave_type() {
		return leave_type;
	}

	public void setLeave_type(Leave_types leave_type) {
		this.leave_type = leave_type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	
}
