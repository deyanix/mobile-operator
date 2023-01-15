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

	public void updateCurrentCustomer(Customer customer) {
		User user = userService.getCurrentUser();
		if (user.getCustomer() != null) {
			customer.setId(user.getCustomer().getId());
			if (user.getCustomer().getAddress() != null) {
				customer.getAddress().setId(user.getCustomer().getAddress().getId());
			}
		}

		addressRepository.save(customer.getAddress());
		customerRepository.save(customer);

		if (user.getCustomer() == null) {
			user.setCustomer(customer);
			userRepository.save(user);
		}
		AppAuthenticationProvider.reloadToken(user);
	}
}
