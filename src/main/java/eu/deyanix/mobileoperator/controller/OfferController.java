package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.dto.AgreementCreation;
import eu.deyanix.mobileoperator.service.OfferService;
import eu.deyanix.mobileoperator.service.UserService;
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
	private UserService userService;
	private OfferService offerService;

	public OfferController(UserService userService, OfferService offerService) {
		this.userService = userService;
		this.offerService = offerService;
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
	public void offers(@RequestBody AgreementCreation request) {
		offerService.createAgreement(request.getOfferId());
	}
}
