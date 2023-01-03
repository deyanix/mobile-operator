package eu.deyanix.mobileoperator.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {
	@Id
	private Long id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private Date birthDate;
	@Column
	private String pesel;
	@ManyToOne
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
}
