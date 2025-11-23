package dto;

public class DTO_employee_add_result {

	private final int employee_id;
	private final String emp_department_id;
	private final boolean eventFailed;

	public DTO_employee_add_result(int employee_id, String emp_department_id, boolean eventFailed) {
		super();
		this.employee_id = employee_id;
		this.emp_department_id = emp_department_id;
		this.eventFailed = eventFailed;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public String getEmp_department_id() {
		return emp_department_id;
	}

	public boolean isEventFailed() {
		return eventFailed;
	}


}
