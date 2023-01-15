package eu.deyanix.mobileoperator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ADDRESSES")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String street;
	@Column(nullable = false)
	@NotBlank(message = "Numer budynku nie może być pusty")
	private String buildingNumber;
	@Column
	private String apartmentNumber;
	@Column(nullable = false)
	@NotBlank(message = "Kod pocztowy nie może być pusty")
	private String postalCode;
	@Column(nullable = false)
	@NotBlank(message = "Miejscowość nie może być pusta")
	private String city;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNumber() {
		if (apartmentNumber != null && !apartmentNumber.isBlank()) {
			return String.join("/", buildingNumber, apartmentNumber);
		}
		return buildingNumber;
	}

	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", street='" + street + '\'' +
				", buildingNumber='" + buildingNumber + '\'' +
				", apartmentNumber='" + apartmentNumber + '\'' +
				", postalCode='" + postalCode + '\'' +
				", city='" + city + '\'' +
				'}';
	}
}
