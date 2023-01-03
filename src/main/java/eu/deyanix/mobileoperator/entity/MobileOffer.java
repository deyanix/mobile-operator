package eu.deyanix.mobileoperator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MOBILE_OFFERS")
public class MobileOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
