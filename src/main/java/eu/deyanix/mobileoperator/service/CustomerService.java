package eu.deyanix.mobileoperator.service;

import eu.deyanix.mobileoperator.entity.Customer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AddressRepository;
import eu.deyanix.mobileoperator.repository.AgreementRepository;
import eu.deyanix.mobileoperator.repository.CustomerRepository;
import eu.deyanix.mobileoperator.repository.UserRepository;
import eu.deyanix.mobileoperator.security.AppAuthenticationProvider;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	private UserService userService;
	private CustomerRepository customerRepository;
	private AddressRepository addressRepository;
	private UserRepository userRepository;

	public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository, UserRepository userRepository, AgreementRepository agreementRepository, UserService userService) {
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public Iterable<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	public void updateUserCustomer(User user, Customer newCustomer) {
		if (user.getCustomer() != null) {
			newCustomer.setId(user.getCustomer().getId());
			if (user.getCustomer().getAddress() != null) {
				newCustomer.getAddress().setId(user.getCustomer().getAddress().getId());
			}
		}

		addressRepository.save(newCustomer.getAddress());
		customerRepository.save(newCustomer);
		if (user.getCustomer() == null) {
			user.setCustomer(newCustomer);
			userRepository.save(user);
		}
		AppAuthenticationProvider.reloadToken(user);
	}

	public void updateCurrentCustomer(Customer customer) {
		User user = userService.getCurrentUser();
		updateUserCustomer(user, customer);
	}

	public void deleteCustomer(User user) {
		Customer customer = user.getCustomer();
		user.setCustomer(null);
		AppAuthenticationProvider.reloadToken(user);

		userRepository.save(user);
		customerRepository.delete(customer);
	}
}
