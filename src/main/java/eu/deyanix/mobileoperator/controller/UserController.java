package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.criteria.UserAgreementCriteria;
import eu.deyanix.mobileoperator.entity.Address;
import eu.deyanix.mobileoperator.entity.Customer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.service.AgreementService;
import eu.deyanix.mobileoperator.service.CustomerService;
import eu.deyanix.mobileoperator.service.UserService;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class UserController {
	private UserService userService;
	private AgreementService agreementService;
	private CustomerService customerService;

	public UserController(UserService userService, AgreementService agreementService, CustomerService customerService) {
		this.userService = userService;
		this.agreementService = agreementService;
		this.customerService = customerService;
	}

	@RequestMapping("/user")
	public String user(Model model) {
		User user = userService.getCurrentUser();
		Customer customer = user.getCustomer();
		Address address = Optional.ofNullable(customer)
				.map(Customer::getAddress)
				.orElse(null);

		model.addAttribute("user", user);
		model.addAttribute("customer", customer);
		model.addAttribute("address", address);
		return "user/information";
	}

	@GetMapping("/user/edit")
	public String editUser(Model model) {
		User user = userService.getCurrentUser();
		model.addAttribute("customer", Optional.ofNullable(user.getCustomer()).orElse(new Customer()));
		return "user/edit";
	}

	@PostMapping("/user/edit")
	public String updateUser(@ModelAttribute("customer") @Valid Customer customer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("errors", result);
			return "user/edit";
		}
		customerService.updateCurrentCustomer(customer);
		return "redirect:/user";
	}

	@GetMapping("/user/agreements")
	public String getAgreements(Model model, UserAgreementCriteria criteria) {
		model.addAttribute("criteria", criteria);
		model.addAttribute("agreements", agreementService.getUserAgreements(criteria));
		return "user/agreements";
	}
}
