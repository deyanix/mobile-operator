package eu.deyanix.mobileoperator.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OFFERS")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer duration;
    @Column(nullable = false)
    private Float monthlyCost;
    @Column(nullable = false)
    private Float activationFee;
    @Column
    private Integer internetLimit;
    @OneToOne(mappedBy = "offer")
    @JsonManagedReference
    private MobileOffer mobileOffer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Float getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(Float monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public Float getActivationFee() {
        return activationFee;
    }

    public void setActivationFee(Float activationFee) {
        this.activationFee = activationFee;
    }

    public Integer getInternetLimit() {
        return internetLimit;
    }

    public void setInternetLimit(Integer internetLimit) {
        this.internetLimit = internetLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MobileOffer getMobileOffer() {
        return mobileOffer;
    }

    public void setMobileOffer(MobileOffer mobileOffer) {
        this.mobileOffer = mobileOffer;
    }
}
