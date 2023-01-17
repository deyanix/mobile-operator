package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.creation.SimpleAgreementCreation;
import eu.deyanix.mobileoperator.service.AgreementService;
import eu.deyanix.mobileoperator.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class OfferController {
	private OfferService offerService;
	private AgreementService agreementService;

	public OfferController(OfferService offerService, AgreementService agreementService) {
		this.offerService = offerService;
		this.agreementService = agreementService;
	}

	@RequestMapping("/")
	public String offers(Model model) {
		model.addAttribute("offers", offerService.getInternetOffers());
		model.addAttribute("mobileOffers", offerService.getMobileOffers());
		return "main/offers";
	}

	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/offers/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void offers(@RequestBody SimpleAgreementCreation request) {
		agreementService.createUserAgreement(request.getOfferId());
	}
}
