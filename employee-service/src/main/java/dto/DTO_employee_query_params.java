package dto;

public class DTO_employee_query_params {

	private String sortBy;
	private String order;
	private String department_id;
	private int page;
	private int limit;
	private String email;
	private String name;
	
	public DTO_employee_query_params(){
	
	}

	public DTO_employee_query_params(String sortBy, String order, String department_id, int page, int limit , String email, String name) {
		
		this.sortBy = sortBy;
		this.order = order;
		this.department_id = department_id;
		this.page = page;
		this.limit = limit;
		this.email = email;
		this.name = name;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDepartmentId() {
		return department_id;
	}

	public void setDepartmentId(String department) {
		this.department_id = department;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
