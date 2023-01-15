package eu.deyanix.mobileoperator.service;

import eu.deyanix.mobileoperator.criteria.AgreementCriteria;
import eu.deyanix.mobileoperator.criteria.UserAgreementCriteria;
import eu.deyanix.mobileoperator.criteria.UserCriteria;
import eu.deyanix.mobileoperator.entity.Agreement;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AgreementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgreementService {
	private UserService userService;
	private AgreementRepository agreementRepository;

	public AgreementService(UserService userService, AgreementRepository agreementRepository) {
		this.userService = userService;
		this.agreementRepository = agreementRepository;
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
}
