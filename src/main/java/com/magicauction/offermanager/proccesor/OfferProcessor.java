package com.magicauction.offermanager.proccesor;

import com.magicauction.offermanager.entity.Offer;
import com.magicauction.offermanager.entity.Publication;
import com.magicauction.offermanager.entity.User;
import com.magicauction.offermanager.entity.dtos.OfferDto;
import com.magicauction.offermanager.entity.exceptions.UserNotFoundException;
import com.magicauction.offermanager.entity.repository.OfferRepository;
import com.magicauction.offermanager.entity.repository.PublicationRepository;
import com.magicauction.offermanager.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferProcessor implements IOfferProcessor{

    private final OfferRepository repository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    @Autowired
    public OfferProcessor(OfferRepository repository, UserRepository userRepository, PublicationRepository publicationRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
    }

    @Override
    public Optional<OfferDto> findOfferById(Long offerId) {
        return repository.findById(offerId).map(this::fromEntity);
    }

    @Override
    public List<OfferDto> findAllOffers() {
        return repository.findAll().stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<OfferDto> createNewOffer(OfferDto inputOffer) throws UserNotFoundException {
        if(isBuyerEqualFromSeller(inputOffer) || isPublicationsEmpty(inputOffer))
            return Optional.empty();

        //TODO: check that pub is of correct publisher
        Offer toSave = toEntity(inputOffer);
        Offer offer = repository.save(toSave);
        return Optional.of(fromEntity(offer));
    }


    @Override
    public Optional<OfferDto> updateOffer(Long offerId, OfferDto inputOffer) {
        if(isBuyerEqualFromSeller(inputOffer) || isPublicationsEmpty(inputOffer))
            return Optional.empty();

        return null;
    }

    @Override
    public Boolean deleteOfferById(Long offerId) {
        return null;
    }

    @Override
    public List<OfferDto> findOffersByPublisher(Long publisherId) {
        return null;
    }

    @Override
    public Optional<OfferDto> findOfferByIdAndByPublisher(Long offerId, Long publisherId) {
        return null;
    }

    @Override
    public List<OfferDto> findOfferByPublisherIdAndByBuyerId(Long publisherId, Long buyerId) {
        return null;
    }

    @Override
    public List<OfferDto> updateOffersToFinished(List<Long> idsToUpdate) {
        return null;
    }

    public OfferDto fromEntity(Offer entity){
        List<Long> pubIds = entity.getPublications().stream().map(Publication::getPublicationId).collect(Collectors.toList());
        return new OfferDto(entity.getOfferId(),
                entity.getPublisher().getUserId(),
                entity.getBuyer().getUserId(),
                pubIds,
                entity.getCreatedAt(),
                entity.getFinishedAt(),
                entity.getStatus()
        );
    }

    private boolean isBuyerEqualFromSeller(OfferDto inputOffer) {
        return inputOffer.buyerId().equals(inputOffer.publisherId());
    }

    private Offer toEntity(OfferDto inputOffer) throws UserNotFoundException {
        User buyer = userRepository.findById(inputOffer.buyerId()).orElseThrow(UserNotFoundException::new);
        User publisher = userRepository.findById(inputOffer.publisherId()).orElseThrow(UserNotFoundException::new);
        List<Publication> pubs = publicationRepository.findAllById(inputOffer.publications());
        return new Offer(publisher, buyer, pubs);
    }

    private boolean isPublicationsEmpty(OfferDto inputOffer) {
        return inputOffer.publications().isEmpty();
    }

}
