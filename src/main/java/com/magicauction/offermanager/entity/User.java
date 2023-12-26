package com.magicauction.offermanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="USERS")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String name;

    private String tradeArea;

    //Publisher View
    //Publis que hice
    @OneToMany(mappedBy = "publisher")
    private List<Publication> myPublications;

    //Ofertas que me hicieron
    @OneToMany(mappedBy = "publisher")
    private List<Offer> myPublisherOffers;

    //Buyer View
    //Ofertas que hice
    @OneToMany(mappedBy = "buyer")
    private List<Offer> myBuyerOffers;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", tradeArea='" + tradeArea + '\'' +
                ", myPublications=" + myPublications +
                ", receivedOffers=" + myPublisherOffers +
                ", myOffers=" + myBuyerOffers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(name, user.name) && Objects.equals(tradeArea, user.tradeArea) && Objects.equals(myPublications, user.myPublications) && Objects.equals(myPublisherOffers, user.myPublisherOffers) && Objects.equals(myBuyerOffers, user.myBuyerOffers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, tradeArea, myPublications, myPublisherOffers, myBuyerOffers);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public List<Publication> getMyPublications() {
        return myPublications;
    }

    public void setMyPublications(List<Publication> myPublications) {
        this.myPublications = myPublications;
    }

    public List<Offer> getMyPublisherOffers() {
        return myPublisherOffers;
    }

    public void setMyPublisherOffers(List<Offer> myPublisherOffers) {
        this.myPublisherOffers = myPublisherOffers;
    }

    public List<Offer> getMyBuyerOffers() {
        return myBuyerOffers;
    }

    public void setMyBuyerOffers(List<Offer> myBuyerOffers) {
        this.myBuyerOffers = myBuyerOffers;
    }

    public User(Long id, String name, String zone, List<Publication> myPublications, List<Offer> receivedOffers, List<Offer> myOffers) {
        this.userId = id;
        this.name = name;
        this.tradeArea = zone;
        this.myPublications = myPublications;
        this.myPublisherOffers = receivedOffers;
        this.myBuyerOffers = myOffers;
    }

    public User() {
    }
}
