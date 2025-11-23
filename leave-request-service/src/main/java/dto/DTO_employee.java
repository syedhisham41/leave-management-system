package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTO_employee {

	@JsonProperty("emp_id")
	private int id;

	@JsonProperty("emp_name")
	private String name;

	@JsonProperty("emp_department_id")
	private String department;

	@JsonProperty("emp_email")
	private String email_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
