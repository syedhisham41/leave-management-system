package dto;

public class DTO_department_query_params {

	private String order;
	private String search;
	private String sortBy;
	private int page;
	private int limit;
	
	public DTO_department_query_params(){

		this.order = "asc";
		this.sortBy = "emp_department_id";
		this.page = 1;
		this.limit = 10;
	}

	public DTO_department_query_params(String order, String search, String sortBy, int page, int limit) {
		
		this.order = order;
		this.search = search;
		this.sortBy = sortBy;
		this.page = page;
		this.limit = limit;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
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
	
}
