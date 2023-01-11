package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.dto.UserAgreementCriteria;
import eu.deyanix.mobileoperator.entity.Address;
import eu.deyanix.mobileoperator.entity.Customer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AddressRepository;
import eu.deyanix.mobileoperator.repository.AgreementRepository;
import eu.deyanix.mobileoperator.repository.CustomerRepository;
import eu.deyanix.mobileoperator.repository.UserRepository;
import eu.deyanix.mobileoperator.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/user")
	public String user(Model model) {
		User user = userService.getCurrentUser();
		Customer customer = user.getCustomer();
		Address address = Optional.of(customer)
				.map(Customer::getAddress)
				.orElse(null);

		model.addAttribute("user", user);
		model.addAttribute("customer", customer);
		model.addAttribute("address", address);
		return "user";
	}

	@GetMapping("/user/edit")
	public String editUser(Model model) {
		User user = userService.getCurrentUser();
		model.addAttribute("customer", user.getCustomer());
		return "edit-user";
	}

	@PostMapping("/user/edit")
	public String updateUser(Customer customer) {
		userService.updateCustomer(customer);
		return "redirect:/";
	}

	@GetMapping("/user/agreements")
	public String getAgreements(Model model, UserAgreementCriteria criteria) {
		model.addAttribute("agreements", userService.getAgreements(criteria));
		return "user-agreements";
	}
}
