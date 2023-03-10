package eu.deyanix.mobileoperator.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "MOBILE_OFFERS")
public class MobileOffer {
    @Id
    @Column(name = "OFFER_ID")
    private Long id;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "OFFER_ID")
    @JsonBackReference
    private Offer offer;
    @Column
    private Integer smsLimit;
    @Column
    private Integer mmsLimit;
    @Column
    private Integer smsRoamingLimit;
    @Column
    private Integer mmsRoamingLimit;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Integer getSmsLimit() {
        return smsLimit;
    }

    public void setSmsLimit(Integer smsLimit) {
        this.smsLimit = smsLimit;
    }

    public Integer getMmsLimit() {
        return mmsLimit;
    }

    public void setMmsLimit(Integer mmsLimit) {
        this.mmsLimit = mmsLimit;
    }

    public Integer getSmsRoamingLimit() {
        return smsRoamingLimit;
    }

    public void setSmsRoamingLimit(Integer smsRoamingLimit) {
        this.smsRoamingLimit = smsRoamingLimit;
    }

    public Integer getMmsRoamingLimit() {
        return mmsRoamingLimit;
    }

    public void setMmsRoamingLimit(Integer mmsRoamingLimit) {
        this.mmsRoamingLimit = mmsRoamingLimit;
    }

}
