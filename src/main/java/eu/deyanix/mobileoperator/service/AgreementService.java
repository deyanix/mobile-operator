package eu.deyanix.mobileoperator.service;

import eu.deyanix.mobileoperator.creation.AgreementCreation;
import eu.deyanix.mobileoperator.criteria.AgreementCriteria;
import eu.deyanix.mobileoperator.criteria.UserAgreementCriteria;
import eu.deyanix.mobileoperator.entity.Agreement;
import eu.deyanix.mobileoperator.entity.Offer;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AgreementRepository;
import eu.deyanix.mobileoperator.repository.CustomerRepository;
import eu.deyanix.mobileoperator.repository.OfferRepository;
import eu.deyanix.mobileoperator.security.AppAuthenticationProvider;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AgreementService {
	private UserService userService;
	private AgreementRepository agreementRepository;
	private OfferRepository offerRepository;
	private CustomerRepository customerRepository;

	public AgreementService(UserService userService, AgreementRepository agreementRepository, OfferRepository offerRepository, CustomerRepository customerRepository) {
		this.userService = userService;
		this.agreementRepository = agreementRepository;
		this.offerRepository = offerRepository;
		this.customerRepository = customerRepository;
	}

	private Sort createAgreementsSort(UserAgreementCriteria criteria) {
		String orderBy = criteria.getOrderBy();
		Sort.Direction direction = Sort.Direction
				.fromOptionalString(criteria.getOrderDirection())
				.orElse(Sort.Direction.ASC);

		if (List.of("signingDate", "expirationDate").contains(orderBy)) {
			return Sort.by(direction, orderBy);
		}
		if (orderBy.equals("customer")) {
			return Sort.by(direction, "customer.firstName", "customer.lastName");
		}
		return Sort.unsorted();
	}

	private PageRequest createAgreementsPageRequest(UserAgreementCriteria criteria) {
		Sort sort = createAgreementsSort(criteria);
		return PageRequest.of(criteria.getPage(), criteria.getPageSize(), sort);
	}

	public Page<Agreement> getUserAgreements(UserAgreementCriteria criteria) {
		User user = userService.getCurrentUser();
		PageRequest pageRequest = createAgreementsPageRequest(criteria);

		if (criteria.getMobile() == null) {
			return agreementRepository.findAllByCustomer(user.getCustomer(), pageRequest);
		}
		if (criteria.getMobile()) {
			return agreementRepository.findAllMobileByCustomer(user.getCustomer(), pageRequest);
		}
		return agreementRepository.findAllInternetByCustomer(user.getCustomer(), pageRequest);
	}

	public Page<Agreement> getAgreements(AgreementCriteria criteria) {
		PageRequest pageRequest = createAgreementsPageRequest(criteria);

		if (criteria.getMobile() == null) {
			return agreementRepository.findAll(pageRequest);
		}
		if (criteria.getMobile()) {
			return agreementRepository.findAllMobile(pageRequest);
		}
		return agreementRepository.findAllInternet(pageRequest);
	}

	public Optional<Agreement> getAgreement(Long id) {
		return agreementRepository.findById(id);
	}

	public void createUserAgreement(Long offerId) {
		User user = userService.getCurrentUser();
		Offer offer = offerRepository.findById(offerId).orElseThrow(EntityNotFoundException::new);
		LocalDate signingDate = LocalDate.now();
		LocalDate expirationDate = signingDate.plusMonths(offer.getDuration());

		Agreement agreement = new Agreement();
		agreement.setOffer(offer);
		agreement.setCustomer(user.getCustomer());
		agreement.setSigningDate(signingDate);
		agreement.setExpirationDate(expirationDate);
		agreementRepository.save(agreement);
	}

	public Agreement mapCreationToAgreement(AgreementCreation creation, Agreement agreement) {
		agreement.setSigningDate(creation.getSigningDate());
		agreement.setExpirationDate(creation.getExpirationDate());
		agreement.setOffer(offerRepository.findById(creation.getOfferId()).orElseThrow(EntityNotFoundException::new));
		agreement.setCustomer(customerRepository.findById(creation.getCustomerId()).orElseThrow(EntityNotFoundException::new));
		return agreement;
	}

	public Agreement mapCreationToAgreement(AgreementCreation creation) {
		return mapCreationToAgreement(creation, new Agreement());
	}

	public void saveAgreement(Agreement agreement) {
		agreementRepository.save(agreement);
	}

	public void deleteAgreement(Agreement agreement) {
		agreementRepository.delete(agreement);
	}
}
