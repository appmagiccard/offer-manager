package com.magicauction.offermanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
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
    private Set<Offer> offers;

    private String cardName;

    private CardState cardState;

    private Float price;

    private Date createdOn;

    private Date finishedOn;


    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Publication(Long publicationId, User publisher, Set<Offer> offers, String cardName) {
        this.publicationId = publicationId;
        this.publisher = publisher;
        this.offers = offers;
        this.cardName = cardName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(publicationId, that.publicationId) && Objects.equals(publisher, that.publisher) && Objects.equals(offers, that.offers) && Objects.equals(cardName, that.cardName) && cardState == that.cardState && Objects.equals(price, that.price) && Objects.equals(createdOn, that.createdOn) && Objects.equals(finishedOn, that.finishedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicationId, publisher, offers, cardName, cardState, price, createdOn, finishedOn);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "publicationId=" + publicationId +
                ", publisher=" + publisher +
                ", offers=" + offers +
                ", cardName='" + cardName + '\'' +
                ", cardState=" + cardState +
                ", price=" + price +
                ", createdOn=" + createdOn +
                ", finishedOn=" + finishedOn +
                '}';
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(Date finishedOn) {
        this.finishedOn = finishedOn;
    }

    public Publication(Long publicationId, User publisher, Set<Offer> offers, String cardName, CardState cardState, Float price, Date createdOn, Date finishedOn) {
        this.publicationId = publicationId;
        this.publisher = publisher;
        this.offers = offers;
        this.cardName = cardName;
        this.cardState = cardState;
        this.price = price;
        this.createdOn = createdOn;
        this.finishedOn = finishedOn;
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

    public Set<Offer> getPubOffers() {
        return offers;
    }

    public void setPubOffers(Set<Offer> pubOffers) {
        this.offers = pubOffers;
    }

    public Publication(Long publicationId, User publisher, Set<Offer> offers) {
        this.publicationId = publicationId;
        this.publisher = publisher;
        this.offers = offers;
    }


    public Publication() {
    }

    public Publication(Long publicationId, User publisher, String cardName, CardState cardState) {
        this.publicationId = publicationId;
        this.publisher = publisher;
        this.cardName = cardName;
        this.cardState = cardState;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public CardState getCardState() {
        return cardState;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public Publication(Long publicationId, User publisher, Set<Offer> offers, String cardName, CardState cardState) {
        this.publicationId = publicationId;
        this.publisher = publisher;
        this.offers = offers;
        this.cardName = cardName;
        this.cardState = cardState;
    }
}
