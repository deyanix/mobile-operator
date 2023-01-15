package eu.deyanix.mobileoperator.service;

import eu.deyanix.mobileoperator.criteria.UserCriteria;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getCurrentUser() {
		return Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.filter(User.class::isInstance)
				.map(User.class::cast)
				.map(User::getId)
				.flatMap(this::getUser)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
	}

	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	private Sort createUsersSort(UserCriteria criteria) {
		String orderBy = criteria.getOrderBy();
		Sort.Direction direction = Sort.Direction
				.fromOptionalString(criteria.getOrderDirection())
				.orElse(Sort.Direction.ASC);

		if (orderBy.equals("username")) {
			return Sort.by(direction, "username");
		}
		if (orderBy.equals("customer")) {
			return Sort.by(direction, "customer.firstName", "customer.lastName");
		}
		return Sort.unsorted();
	}

	private PageRequest createUsersPageRequest(UserCriteria criteria) {
		Sort sort = createUsersSort(criteria);
		return PageRequest.of(criteria.getPage(), criteria.getPageSize(), sort);
	}

	public Page<User> getUsers(UserCriteria criteria) {
		return userRepository.findAll(createUsersPageRequest(criteria));
	}
}
