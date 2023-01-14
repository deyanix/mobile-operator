package eu.deyanix.mobileoperator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	@RequestMapping("/admin")
	public String index() {
		return "redirect:/admin/users";
	}

	@RequestMapping("/admin/users")
	public String users(Model model) {
		return "index";
	}

	@RequestMapping("/admin/users/create")
	public String createUser(Model model) {
		return "index";
	}

	@RequestMapping("/admin/users/:id")
	public String previewUser(Model model) {
		return "index";
	}

	@RequestMapping("/admin/users/:id/edit")
	public String editUser(Model model) {
		return "index";
	}

	@RequestMapping("/admin/customer/:id/edit")
	public String editCustomer() {
		return "index";
	}

	@RequestMapping("/admin/agreements")
	public String agreements() {
		return "index";
	}

	@RequestMapping("/admin/agreements/create")
	public String createAgreement(Model model) {
		return "index";
	}

	@RequestMapping("/admin/agreements/:id")
	public String previewAgreement(Model model) {
		return "index";
	}

	@RequestMapping("/admin/agreements/:id/edit")
	public String editAgreement(Model model) {
		return "index";
	}
}
