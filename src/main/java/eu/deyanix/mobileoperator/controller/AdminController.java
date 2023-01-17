package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.creation.AgreementCreation;
import eu.deyanix.mobileoperator.criteria.AgreementCriteria;
import eu.deyanix.mobileoperator.criteria.UserCriteria;
import eu.deyanix.mobileoperator.creation.UserCreation;
import eu.deyanix.mobileoperator.entity.Address;
import eu.deyanix.mobileoperator.entity.Agreement;
import eu.deyanix.mobileoperator.entity.Customer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.group.CreateUser;
import eu.deyanix.mobileoperator.group.UpdateUser;
import eu.deyanix.mobileoperator.service.AgreementService;
import eu.deyanix.mobileoperator.service.CustomerService;
import eu.deyanix.mobileoperator.service.OfferService;
import eu.deyanix.mobileoperator.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminController {
	private AgreementService agreementService;
	private CustomerService customerService;
	private UserService userService;
	private OfferService offerService;

	public AdminController(AgreementService agreementService, CustomerService customerService, UserService userService, OfferService offerService) {
		this.agreementService = agreementService;
		this.customerService = customerService;
		this.userService = userService;
		this.offerService = offerService;
	}

	@RequestMapping("/admin")
	public String index() {
		return "redirect:/admin/users";
	}

	@RequestMapping("/admin/users")
	public String users(Model model, @ModelAttribute("criteria") UserCriteria criteria) {
		Page<User> users = userService.getUsers(criteria);
		model.addAttribute("users", users);
		return "admin/user/users";
	}

	@RequestMapping("/admin/users/{id}")
	public String previewUser(Model model, @PathVariable Long id) {
		User user = userService.getUser(id)
				.orElseThrow(EntityNotFoundException::new);
		Customer customer = user.getCustomer();
		Address address = Optional.ofNullable(customer)
				.map(Customer::getAddress)
				.orElse(null);

		model.addAttribute("user", user);
		model.addAttribute("customer", customer);
		model.addAttribute("address", address);
		return "admin/user/user";
	}

	@RequestMapping("/admin/users/create")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("authorities", userService.getAuthorities());
		return "admin/user/edit-user";
	}

	public ModelAndView createEditUserView(User user) {
		ModelAndView model = new ModelAndView("admin/user/edit-user");
		model.addObject("user", user);
		model.addObject("authorities", userService.getAuthorities());
		return model;
	}

	@PostMapping("/admin/users/create")
	public ModelAndView createUser(@RequestBody @ModelAttribute("creation") @Validated(CreateUser.class) UserCreation creation,
							 BindingResult result) {
		User user = userService.mapCreationToUser(creation);
		userService.validateCreation(user, creation, result);
		if (result.hasErrors()) {
			ModelAndView modelAndView = createEditUserView(user);
			modelAndView.addObject("errors", result.getAllErrors());
			return modelAndView;
		}
		userService.saveUser(user);
		return new ModelAndView("redirect:/admin/users");
	}

	@GetMapping("/admin/users/{id}/edit")
	public ModelAndView updateUser(Model model, @PathVariable Long id) {
		return userService.getUser(id)
				.map(this::createEditUserView)
				.orElseThrow(EntityNotFoundException::new);
	}

	@PostMapping("/admin/users/{userId}/edit")
	public String updateUser(@RequestBody @ModelAttribute("creation") @Validated(UpdateUser.class) UserCreation creation,
							 BindingResult result, Model model,
							 @PathVariable Long userId) {
		User user = userService.getUser(userId)
				.map((u) -> userService.mapCreationToUser(creation, u))
				.orElseThrow(EntityNotFoundException::new);
		userService.validateCreation(user, creation, result);
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("authorities", userService.getAuthorities());
			model.addAttribute("errors", result);
			return "admin/user/edit-user";
		}

		userService.saveUser(user);
		return String.format("redirect:/admin/users/%d", userId);
	}

	@PostMapping("/admin/users/{id}/delete")
	public String deleteUser(@PathVariable Long id) {
		User user = userService.getUser(id).orElseThrow(EntityNotFoundException::new);
		userService.deleteUser(user);

		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/{id}/customer")
	public String editCustomer(Model model, @PathVariable Long id) {
		User user = userService.getUser(id).orElseThrow(EntityNotFoundException::new);
		model.addAttribute("user", user);
		model.addAttribute("customer", Optional.ofNullable(user.getCustomer()).orElse(new Customer()));
		return "admin/user/edit-customer";
	}

	@PostMapping("/admin/users/{userId}/customer")
	public String updateCustomer(
			@RequestBody @ModelAttribute("customer") @Valid Customer customer,
			BindingResult result, Model model,
			@PathVariable Long userId
	) {
		User user = userService.getUser(userId).orElseThrow(EntityNotFoundException::new);
		model.addAttribute("user", user);
		if (result.hasErrors()) {
			model.addAttribute("errors", result);
			return "admin/user/edit-customer";
		}
		customerService.updateUserCustomer(user, customer);
		return String.format("redirect:/admin/users/%d", userId);
	}

	@PostMapping("/admin/users/{id}/delete-customer")
	public String deleteCustomer(@PathVariable Long id) {
		User user = userService.getUser(id).orElseThrow(EntityNotFoundException::new);
		customerService.deleteCustomer(user);

		return String.format("redirect:/admin/users/%d", id);
	}

	@RequestMapping("/admin/agreements")
	public String agreements(Model model, @ModelAttribute("criteria") AgreementCriteria criteria) {
		Page<Agreement> agreements = agreementService.getAgreements(criteria);
		model.addAttribute("agreements", agreements);
		return "admin/agreement/agreements";
	}

	@GetMapping("/admin/agreements/create")
	public String createAgreement(Model model) {
		model.addAttribute("agreement", new AgreementCreation());
		model.addAttribute("offers", offerService.getOffers());
		model.addAttribute("customers", customerService.getCustomers());
		return "admin/agreement/edit-agreement";
	}

	@PostMapping("/admin/agreements/create")
	public String createAgreement(
			@RequestBody @ModelAttribute("agreement") @Valid AgreementCreation creation,
			BindingResult result,
			Model model
	) {
		if (result.hasErrors()) {
			model.addAttribute("agreement", creation);
			model.addAttribute("offers", offerService.getOffers());
			model.addAttribute("customers", customerService.getCustomers());
			model.addAttribute("errors", result);
			return "admin/agreement/edit-agreement";
		}
		agreementService.saveAgreement(agreementService.mapCreationToAgreement(creation));
		return "redirect:/admin/agreements";
	}

	@RequestMapping("/admin/agreements/{id}")
	public String previewAgreement(Model model, @PathVariable Long id) {
		Agreement agreement = agreementService.getAgreement(id)
				.orElseThrow(EntityNotFoundException::new);

		model.addAttribute("agreement", agreement);
		return "admin/agreement/agreement";
	}

	@GetMapping("/admin/agreements/{id}/edit")
	public String editAgreement(Model model, @PathVariable Long id) {
		Agreement agreement = agreementService.getAgreement(id)
				.orElseThrow(EntityNotFoundException::new);

		model.addAttribute("agreement", new AgreementCreation(agreement));
		model.addAttribute("offers", offerService.getOffers());
		model.addAttribute("customers", customerService.getCustomers());
		return "admin/agreement/edit-agreement";
	}

	@PostMapping("/admin/agreements/{id}/edit")
	public String editAgreement(
			@RequestBody @ModelAttribute("agreement") @Valid AgreementCreation creation,
			BindingResult result,
			Model model,
			@PathVariable Long id
	) {
		Agreement agreement = agreementService.getAgreement(id)
				.orElseThrow(EntityNotFoundException::new);

		if (result.hasErrors()) {
			model.addAttribute("agreement", creation);
			model.addAttribute("offers", offerService.getOffers());
			model.addAttribute("customers", customerService.getCustomers());
			model.addAttribute("errors", result);
			return "admin/agreement/edit-agreement";
		}
		agreementService.saveAgreement(agreementService.mapCreationToAgreement(creation, agreement));
		return "redirect:/admin/agreements";
	}
}
