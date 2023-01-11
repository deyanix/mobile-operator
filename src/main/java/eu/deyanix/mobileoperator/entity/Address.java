package eu.deyanix.mobileoperator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESSES")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String street;
	@Column(nullable = false)
	private String buildingNumber;
	@Column
	private String apartmentNumber;
	@Column(nullable = false)
	private String postalCode;
	@Column(nullable = false)
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
		if (apartmentNumber != null && !apartmentNumber.isEmpty()) {
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
