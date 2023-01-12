package eu.deyanix.mobileoperator.dto;

import org.springframework.web.bind.annotation.RequestParam;

public class UserAgreementCriteria {
	private int pageSize = 10;
	private int page = 0;
	private Boolean mobile = null;
	private String orderBy = "signingDate";
	private String orderDirection = "DESC";

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

	public Boolean getMobile() {
		return mobile;
	}

	public void setMobile(Boolean mobile) {
		this.mobile = mobile;
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
