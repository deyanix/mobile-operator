package eu.deyanix.mobileoperator.criteria;

public class SearchCriteria {
	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final int DEFAULT_PAGE = 0;
	private int pageSize;
	private int page;
	private String orderBy;
	private String orderDirection;

	public SearchCriteria(int pageSize, int page, String orderBy, String orderDirection) {
		this.pageSize = pageSize;
		this.page = page;
		this.orderBy = orderBy;
		this.orderDirection = orderDirection;
	}

	public SearchCriteria(int pageSize, int page) {
		this(pageSize, page, null, null);
	}

	public SearchCriteria(String orderBy, String orderDirection) {
		this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE, orderBy, orderDirection);
	}

	public SearchCriteria() {
		this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
}
