package eu.deyanix.mobileoperator.dto;

import eu.deyanix.mobileoperator.group.CreateUser;
import eu.deyanix.mobileoperator.group.UpdateUser;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class UserCreation {
	@NotBlank(message = "Nazwa użytkownika nie może być pusta", groups = {UpdateUser.class, CreateUser.class})
	private String username;
	@NotBlank(message = "Hasło jest obowiązkowe", groups = {CreateUser.class})
	private String password;
	private String repeatedPassword;
	private Set<Long> authorities = Set.of();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public boolean isMatchPassword() {
		return password.equals(repeatedPassword);
	}

	public Set<Long> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Long> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "UserCreation{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", repeatedPassword='" + repeatedPassword + '\'' +
				", authorities=" + authorities +
				'}';
	}
}
