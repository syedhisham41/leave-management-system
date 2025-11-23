package model;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

	@JsonProperty("emp_id")
	private int id;

	@JsonProperty("emp_name")
	private String name;

	@JsonProperty("emp_joining_date")
	private String joining_date;

	@JsonProperty("emp_department_id")
	private String emp_department_id;
	
	@JsonProperty("emp_email")
	private String email;

	@JsonProperty("updated_at")
	private LocalDateTime updated_at;
	

	public Employee() {}

	public Employee(int id, String name, String joining_date, String department_id, String email, LocalDateTime updated_at) {
		this.id = id;
		this.name = name;
		this.joining_date = joining_date;
		this.emp_department_id = department_id;
		this.email = email;
		this.updated_at = updated_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJoining_date() {
		return joining_date;
	}

	public void setJoining_date(String joining_date) {
		this.joining_date = joining_date;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmp_department_id() {
		return emp_department_id;
	}

	public void setEmp_department_id(String emp_department_id) {
		this.emp_department_id = emp_department_id;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	
	

}
