package eu.deyanix.mobileoperator.service;

import eu.deyanix.mobileoperator.entity.Agreement;
import eu.deyanix.mobileoperator.entity.Offer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AgreementRepository;
import eu.deyanix.mobileoperator.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OfferService {
	private UserService userService;
	private OfferRepository offerRepository;
	private AgreementRepository agreementRepository;

	public OfferService(UserService userService, OfferRepository offerRepository, AgreementRepository agreementRepository) {
		this.userService = userService;
		this.offerRepository = offerRepository;
		this.agreementRepository = agreementRepository;
	}

	public Iterable<Offer> getInternetOffers() {
		return offerRepository.findAllInternet();
	}

	public Iterable<Offer> getMobileOffers() {
		return offerRepository.findAllMobile();
	}

	public void createAgreement(Long offerId) {
		User user = userService.getCurrentUser();
		Offer offer = offerRepository.findById(offerId).orElse(null);
		LocalDate signingDate = LocalDate.now();
		LocalDate expirationDate = signingDate.plusMonths(offer.getDuration());

		Agreement agreement = new Agreement();
		agreement.setOffer(offer);
		agreement.setCustomer(user.getCustomer());
		agreement.setSigningDate(signingDate);
		agreement.setExpirationDate(expirationDate);
		agreementRepository.save(agreement);
	}
}
