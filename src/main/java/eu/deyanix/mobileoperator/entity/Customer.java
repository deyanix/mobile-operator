package eu.deyanix.mobileoperator.entity;

import jakarta.persistence.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@NotBlank(message = "Imię nie może być puste")
	private String firstName;
	@Column(nullable = false)
	@NotBlank(message = "Nazwisko nie może być puste")
	private String lastName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@NotNull(message = "Data urodzenia nie może być pusta")
	@Past(message = "Data urodzenia musi być z przeszłości")
	private Date birthDate;
	@Column(nullable = false)
	@NotBlank(message = "PESEL nie może być pusty")
	@Size(min = 11, max = 11, message = "PESEL musi mieć 11 znaków")
	@Pattern(regexp = "\\d+", message = "PESEL musi się składać z cyfr")
	private String pesel;
	@ManyToOne(optional = false)
	@Valid
	@NotNull
	private Address address;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return String.join(" ", firstName, lastName);
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", birthDate=" + birthDate +
				", pesel='" + pesel + '\'' +
				", address=" + address +
				'}';
	}
}
