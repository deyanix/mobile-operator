package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.dto.OfferCreateDTO;
import eu.deyanix.mobileoperator.entity.Offer;
import eu.deyanix.mobileoperator.repository.OfferRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OfferController {
	private OfferRepository offerRepository;

	public OfferController(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}

	@RequestMapping("/offers")
	public String offers(Model model) {
		model.addAttribute("offers", offerRepository.findAllInternet());
		model.addAttribute("mobileOffers", offerRepository.findAllMobile());
		return "offers";
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/offers/create")
	@ResponseBody
	public Offer offers(@RequestBody OfferCreateDTO request) {
		return offerRepository.findById(request.getOfferId()).orElse(null);
	}
}
