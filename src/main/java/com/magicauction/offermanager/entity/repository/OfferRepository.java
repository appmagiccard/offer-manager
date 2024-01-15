package com.magicauction.offermanager.entity.repository;

import com.magicauction.offermanager.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select o from Offer o where o.publisher.userId = :publisherId")
    public List<Offer> findOfferByPublisher(Long publisherId);

    @Query("select o from Offer o where o.buyer.userId = :buyerId")
    public List<Offer> findOfferByBuyer(Long buyerId);

    @Query("select o from Offer o where o.publisher.userId = :publisherId and o.buyer.userId = :buyerId")
    public List<Offer> findOfferByPublisherAndBuyer(Long publisherId, Long buyerId);
}
