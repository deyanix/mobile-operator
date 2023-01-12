package eu.deyanix.mobileoperator.service;

import eu.deyanix.mobileoperator.dto.UserAgreementCriteria;
import eu.deyanix.mobileoperator.entity.Agreement;
import eu.deyanix.mobileoperator.entity.Customer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AddressRepository;
import eu.deyanix.mobileoperator.repository.AgreementRepository;
import eu.deyanix.mobileoperator.repository.CustomerRepository;
import eu.deyanix.mobileoperator.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserService {
	private CustomerRepository customerRepository;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private AgreementRepository agreementRepository;

	public UserService(CustomerRepository customerRepository, AddressRepository addressRepository, UserRepository userRepository, AgreementRepository agreementRepository) {
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.agreementRepository = agreementRepository;
	}

	public User getCurrentUser() {
		return Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.filter(User.class::isInstance)
				.map(User.class::cast)
				.map(User::getId)
				.map(userRepository::findById)
				.flatMap(Function.identity())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
	}

	private PageRequest createAgreementsPageRequest(UserAgreementCriteria criteria) {
		if (List.of("signingDate", "expirationDate").contains(criteria.getOrderBy())) {
			return PageRequest.of(criteria.getPage(), criteria.getPageSize(), Sort.by(Sort.Direction.fromOptionalString(criteria.getOrderDirection()).orElse(Sort.Direction.ASC), criteria.getOrderBy()));
		}
		return PageRequest.of(criteria.getPage(), criteria.getPageSize());
	}

	public Page<Agreement> getAgreements(UserAgreementCriteria criteria) {
		User user = getCurrentUser();
		PageRequest pageRequest = createAgreementsPageRequest(criteria);

		if (criteria.getMobile() == null) {
			return agreementRepository.findAllByCustomer(user.getCustomer(), pageRequest);
		}

		if (criteria.getMobile()) {
			return agreementRepository.findAllMobileByCustomer(user.getCustomer(), pageRequest);
		}
		return agreementRepository.findAllInternetByCustomer(user.getCustomer(), pageRequest);
	}

	public void updateCustomer(Customer customer) {
		User user = getCurrentUser();

		customer.getAddress().setId(user.getCustomer().getAddress().getId());
		addressRepository.save(customer.getAddress());
		customer.setId(user.getCustomer().getId());
		customerRepository.save(customer);
	}
}
