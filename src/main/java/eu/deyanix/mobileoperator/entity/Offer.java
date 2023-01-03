package eu.deyanix.mobileoperator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "OFFERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer duration;
    @Column
    private Float monthlyCost;
    @Column
    private Float activationFee;
    @Column
    private Integer internetLimit;

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
}
