package com.magicauction.offermanager.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="OFFERS")
public class Offer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @ManyToOne
    @JoinColumn(name="publisher", referencedColumnName = "userId")
    private User publisher;

    @ManyToOne
    @JoinColumn(name="buyer", referencedColumnName = "userId")
    private User buyer;

    private Date createdAt;
    private Date finishedAt;

    @Enumerated
    private TradeStatus status;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "publication_offer",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "publication_id"))
    private Set<Publication> publications;



    public Offer() {
    }

    public Offer(User publisher, User buyer, Set<Publication> publications) {
        this.publisher = publisher;
        this.buyer = buyer;
        this.publications = publications;
        this.status = TradeStatus.STARTED;
        this.createdAt = new Date(new java.util.Date().getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(publisher, offer.publisher) && Objects.equals(buyer, offer.buyer) && Objects.equals(createdAt, offer.createdAt) && Objects.equals(finishedAt, offer.finishedAt) && status == offer.status && Objects.equals(publications, offer.publications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publisher, buyer, createdAt, finishedAt, status, publications);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerId=" + offerId +
                ", publisher=" + publisher +
                ", buyer=" + buyer +
                ", createdAt=" + createdAt +
                ", finishedAt=" + finishedAt +
                ", status=" + status +
                ", publications=" + publications +
                '}';
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> pubOffers) {
        this.publications = pubOffers;
    }

    public Offer(Long offerId, User publisher, User buyer, Date createdAt, Date finishedAt, TradeStatus status, Set<Publication> publications) {
        this.offerId = offerId;
        this.publisher = publisher;
        this.buyer = buyer;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.status = status;
        this.publications = publications;
    }
}
