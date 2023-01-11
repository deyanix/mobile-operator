package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.entity.Address;
import eu.deyanix.mobileoperator.entity.Customer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AddressRepository;
import eu.deyanix.mobileoperator.repository.CustomerRepository;
import eu.deyanix.mobileoperator.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UserController {
	private UserRepository userRepository;
	private CustomerRepository customerRepository;
	private AddressRepository addressRepository;

	public UserController(UserRepository userRepository, CustomerRepository customerRepository, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
	}

	@RequestMapping("/user")
	public String user(Authentication authentication, Model model) {
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		User user = userRepository.findById(((User) principal).getId()).orElseThrow();
		Customer customer = user.getCustomer();
		Address address = customer != null ? customer.getAddress() : null;

		String addressLine1 = null;
		String addressLine2 = null;
		if (address != null) {
			if (address.getApartmentNumber() != null && !address.getApartmentNumber().isEmpty()) {
				addressLine1 = String.format("%s %s/%s", address.getStreet(), address.getBuildingNumber(), address.getApartmentNumber());
			} else {
				addressLine1 = String.format("%s %s", address.getStreet(), address.getBuildingNumber());
			}
			addressLine2 = String.format("%s %s", address.getPostalCode(), address.getCity());
		}

		model.addAttribute("user", user);
		model.addAttribute("customer", customer);
		model.addAttribute("address", address);
		model.addAttribute("addressLine1", addressLine1);
		model.addAttribute("addressLine2", addressLine2);
		return "user";
	}

	@GetMapping("/user/edit")
	public String editUser(Authentication authentication, Model model) {
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		User user = userRepository.findById(((User) principal).getId()).orElseThrow();
		Customer customer = user.getCustomer();

		model.addAttribute("customer", customer);
		return "edit-user";
	}

	@PostMapping("/user/edit")
	public String updateUser(Authentication authentication, Customer customer) {
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findById(((User) principal).getId()).orElseThrow();

		customer.getAddress().setId(user.getCustomer().getAddress().getId());
		addressRepository.save(customer.getAddress());

		customer.setId(user.getCustomer().getId());
		customerRepository.save(customer);
		return "redirect:/";
	}
}
