package eu.deyanix.mobileoperator.service;

import eu.deyanix.mobileoperator.entity.Agreement;
import eu.deyanix.mobileoperator.entity.Offer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AgreementRepository;
import eu.deyanix.mobileoperator.repository.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
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

	public Iterable<Offer> getOffers() {
		return offerRepository.findAll();
	}

	public Iterable<Offer> getInternetOffers() {
		return offerRepository.findAllInternet();
	}

	public Iterable<Offer> getMobileOffers() {
		return offerRepository.findAllMobile();
	}
}
