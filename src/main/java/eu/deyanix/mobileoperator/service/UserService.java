package eu.deyanix.mobileoperator.service;

import eu.deyanix.mobileoperator.criteria.UserCriteria;
import eu.deyanix.mobileoperator.creation.UserCreation;
import eu.deyanix.mobileoperator.entity.Authority;
import eu.deyanix.mobileoperator.entity.User;
import eu.deyanix.mobileoperator.repository.AuthorityRepository;
import eu.deyanix.mobileoperator.repository.UserRepository;
import eu.deyanix.mobileoperator.security.AppAuthenticationProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Iterable<Authority> getAuthorities() {
		return authorityRepository.findAll();
	}

	private Set<Authority> getAuthorities(Iterable<Long> ids) {
		Set<Authority> authorities = new HashSet<>();
		authorityRepository.findAllById(ids)
				.forEach(authorities::add);
		return authorities;
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

	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	public Page<User> getUsers(UserCriteria criteria) {
		return userRepository.findAll(createUsersPageRequest(criteria));
	}

	public User mapCreationToUser(UserCreation creation, User user) {
		user.setUsername(creation.getUsername());
		user.setAuthorities(getAuthorities(creation.getAuthorities()));
		if (!creation.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(creation.getPassword()));
		}
		return user;
	}

	public User mapCreationToUser(UserCreation creation) {
		return mapCreationToUser(creation, new User());
	}

	public void validateCreation(User user, UserCreation creation, BindingResult result) {
		boolean isUsedUsername = userRepository.findByUsername(creation.getUsername())
				.map((u) -> !Objects.equals(u.getId(), user.getId()))
				.orElse(false);

		if (isUsedUsername) {
			result.addError(new FieldError(result.getObjectName(), "username", "Nazwa użytkownika jest już wykorzystana"));
		}

		if (!creation.isMatchPassword()) {
			result.addError(new FieldError(result.getObjectName(), "repeatedPassword", "Hasła nie pasują do siebie"));
		}
	}

	public void saveUser(User user) {
		AppAuthenticationProvider.reloadToken(user);
		userRepository.save(user);
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}
}
