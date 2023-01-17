package eu.deyanix.mobileoperator.creation;

import eu.deyanix.mobileoperator.entity.Agreement;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AgreementCreation {
	@NotNull(message = "Umowa musi mieć określoną taryfę")
	private Long offerId = null;
	@NotNull(message = "Umowa musi mieć określonego abonenta")
	private Long customerId = null;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Data podpisania jest obowiązkowa")
	private LocalDate signingDate = null;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Data wygaśnięcia jest obowiązkowa")
	private LocalDate expirationDate = null;

	public AgreementCreation() {
	}

	public AgreementCreation(Agreement agreement) {
		this.offerId = agreement.getOffer().getId();
		this.customerId = agreement.getCustomer().getId();
		this.signingDate = agreement.getSigningDate();
		this.expirationDate = agreement.getExpirationDate();
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public LocalDate getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(LocalDate signingDate) {
		this.signingDate = signingDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
}
