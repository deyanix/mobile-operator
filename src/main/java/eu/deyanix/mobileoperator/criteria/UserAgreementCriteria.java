package eu.deyanix.mobileoperator.criteria;

import org.springframework.web.bind.annotation.RequestParam;

public class UserAgreementCriteria extends SearchCriteria {
	private Boolean mobile = null;

	public UserAgreementCriteria() {
		super("signingDate", "DESC");
	}

	public Boolean getMobile() {
		return mobile;
	}

	public void setMobile(Boolean mobile) {
		this.mobile = mobile;
	}
}
