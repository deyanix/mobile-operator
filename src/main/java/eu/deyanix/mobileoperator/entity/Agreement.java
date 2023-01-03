package eu.deyanix.mobileoperator.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "AGREEMENTS")
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Date sigingDate;
    @Column
    private Date expirationDate;
    @ManyToOne
    private Offer offer;
    @ManyToOne
    private Customer customer;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getSigingDate() {
        return sigingDate;
    }

    public void setSigingDate(Date sigingDate) {
        this.sigingDate = sigingDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
