package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.criteria.AgreementCriteria;
import eu.deyanix.mobileoperator.criteria.UserCriteria;
import eu.deyanix.mobileoperator.entity.Agreement;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.service.AgreementService;
import eu.deyanix.mobileoperator.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	private AgreementService agreementService;
	private UserService userService;

	public AdminController(AgreementService agreementService, UserService userService) {
		this.agreementService = agreementService;
		this.userService = userService;
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

	@RequestMapping("/admin/users/create")
	public String createUser(Model model) {
		return "admin/user/edit-user";
	}

	@RequestMapping("/admin/users/{id}")
	public String previewUser(Model model) {
		return "admin/user/user";
	}

	@RequestMapping("/admin/users/{id}/edit")
	public String editUser(Model model) {
		return "admin/user/edit-user";
	}

	@RequestMapping("/admin/users/{id}/customer")
	public String editCustomer() {
		return "admin/user/edit-customer";
	}

	@RequestMapping("/admin/agreements")
	public String agreements(Model model, @ModelAttribute("criteria") AgreementCriteria criteria) {
		Page<Agreement> agreements = agreementService.getAgreements(criteria);
		model.addAttribute("agreements", agreements);
		return "admin/agreement/agreements";
	}

	@RequestMapping("/admin/agreements/create")
	public String createAgreement(Model model) {
		return "admin/agreement/edit-agreement";
	}

	@RequestMapping("/admin/agreements/{id}")
	public String previewAgreement(Model model) {
		return "admin/agreement/agreement";
	}

	@RequestMapping("/admin/agreements/{id}/edit")
	public String editAgreement(Model model) {
		return "admin/agreement/edit-agreement";
	}
}
