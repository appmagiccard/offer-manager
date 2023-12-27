package com.magicauction.offermanager.proccesor;

import com.magicauction.offermanager.entity.dtos.OfferDto;
import com.magicauction.offermanager.entity.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IOfferProcessor {
    Optional<OfferDto> findOfferById(Long offerId);

    List<OfferDto> findAllOffers();

    Optional<OfferDto> createNewOffer(OfferDto inputOffer) throws UserNotFoundException;

    Optional<OfferDto> updateOffer(Long offerId, OfferDto inputOffer) throws UserNotFoundException;

    Boolean deleteOfferById(Long offerId);

    List<OfferDto> findOffersByPublisher(Long publisherId);

    List<OfferDto> findOffersByBuyer(Long buyerId);

    List<OfferDto> findOfferByPublisherIdAndByBuyerId(Long publisherId, Long buyerId);

    List<OfferDto> updateOffersToFinished(List<Long> idsToUpdate);
}
