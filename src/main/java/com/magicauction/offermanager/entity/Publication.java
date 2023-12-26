package com.magicauction.offermanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="PUBLICATIONS")
public class Publication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publicationId;

    @ManyToOne
    @JoinColumn(name="publisher_id", referencedColumnName = "userId")
    private User publisher;

    @ManyToMany
    @JoinTable(
            name = "publication_offer",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private Set<Offer> offers;

    private String cardName;
    //private long price;

    @Override
    public String toString() {
        return "Publication{" +
                "publicationId=" + publicationId +
                ", publisher=" + publisher +
                ", offer=" + offers +
                ", cardName='" + cardName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(publicationId, that.publicationId) && Objects.equals(publisher, that.publisher) && Objects.equals(offers, that.offers) && Objects.equals(cardName, that.cardName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicationId, publisher, offers, cardName);
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Publication(Long publicationId, User publisher, Set<Offer> offer, String cardName) {
        this.publicationId = publicationId;
        this.publisher = publisher;
        this.offers = offer;
        this.cardName = cardName;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Publication(Long publicationId, User publisher, Set<Offer> offer) {
        this.publicationId = publicationId;
        this.publisher = publisher;
        this.offers = offer;
    }


    public Publication() {
    }
}
