package com.magicauction.offermanager.proccesor;

import com.magicauction.offermanager.controller.OfferController;
import com.magicauction.offermanager.entity.Offer;
import com.magicauction.offermanager.entity.Publication;
import com.magicauction.offermanager.entity.TradeStatus;
import com.magicauction.offermanager.entity.User;
import com.magicauction.offermanager.entity.dtos.OfferDto;
import com.magicauction.offermanager.entity.exceptions.UserNotFoundException;
import com.magicauction.offermanager.entity.repository.OfferRepository;
import com.magicauction.offermanager.entity.repository.PublicationRepository;
import com.magicauction.offermanager.entity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferProcessor implements IOfferProcessor{

    private static final Logger log = LoggerFactory.getLogger(OfferController.class);
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
        return repository.findById(offerId).map(this::toDto);
    }

    @Override
    public List<OfferDto> findAllOffers() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<OfferDto> createNewOffer(OfferDto inputOffer) throws UserNotFoundException {
        if(validateInputOffer(inputOffer)){
            log.info("inputOffer not valid: {}", inputOffer);
            return Optional.empty();
        }
        return Optional.of(toDto(repository.save(toEntity(inputOffer))));
    }

    @Override
    public Optional<OfferDto> updateOffer(Long offerId, OfferDto inputOffer) throws UserNotFoundException {
        if(validateInputOffer(inputOffer)){
            log.info("inputOffer not valid: {}", inputOffer);
            return Optional.empty();
        }
        Optional<Offer> OptOffer = repository.findById(offerId);
        if ((OptOffer.isEmpty())) {
            log.info("Old Offer not found: {} - Creating new Offer!", offerId);
            return this.createNewOffer(inputOffer);
        }
        Offer offer = OptOffer.get();
        List<Publication> pubs = publicationRepository.findAllById(inputOffer.publications());
        offer.setPublications(new HashSet<>(pubs));
        offer.setStatus(inputOffer.status() != null ? inputOffer.status() : TradeStatus.IN_PROGRESS);
        repository.save(offer);
        return Optional.of(toDto(offer));
    }

    @Override
    public Boolean deleteOfferById(Long offerId) {
        Optional<Offer> optOffer = repository.findById(offerId);
        if (optOffer.isPresent()) {
            repository.delete(optOffer.get());
            return true;
        }
        return false;
    }

    @Override
    public List<OfferDto> findOffersByPublisher(Long publisherId) {
        return repository.findOfferByPublisher(publisherId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public List<OfferDto> findOffersByBuyer(Long buyerId) {
        return repository.findOfferByBuyer(buyerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public List<OfferDto> findOfferByPublisherIdAndByBuyerId(Long publisherId, Long buyerId) {
        return repository.findOfferByPublisherAndBuyer(publisherId, buyerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public List<OfferDto> updateOffersToFinished(List<Long> idsToUpdate) {
        return repository.findAllById(idsToUpdate)
                .stream()
                .map(this::updateToFinish)
                .map(repository::save)
                .map(this::toDto)
                .collect(Collectors.toList())
                ;
    }

    private Offer updateToFinish(Offer o) {
        o.setFinishedAt(new Date(new java.util.Date().getTime()));
        o.setStatus(TradeStatus.FINISHED);
        return o;
    }

    public OfferDto toDto(Offer entity){
        List<Long> pubIds = entity.getPublications().stream()
                .map(Publication::getPublicationId)
                .collect(Collectors.toList());
        return new OfferDto(entity.getOfferId(),
                entity.getPublisher().getUserId(),
                entity.getBuyer().getUserId(),
                pubIds,
                entity.getCreatedAt(),
                entity.getFinishedAt(),
                entity.getStatus()
        );
    }

    private boolean AreBuyerAndPublisherEquals(OfferDto inputOffer) {
        return inputOffer.buyerId().equals(inputOffer.publisherId());
    }

    private Offer toEntity(OfferDto inputOffer) throws UserNotFoundException {
        User buyer = findUserById(inputOffer.buyerId());
        User publisher = findUserById(inputOffer.publisherId());
        List<Publication> pubs = findPublicationsByIds((inputOffer.publications()));
        return new Offer(publisher, buyer, new HashSet<>(pubs));
    }

    private List<Publication> findPublicationsByIds(List<Long> publications) {
        //Puede cambiar a una llamada REST cuando exista el servicio
        return publicationRepository.findAllById(publications);
    }

    private User findUserById(Long id) throws UserNotFoundException {
        //Puede cambiar a una llamada REST cuando exista el servicio
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private boolean isPublicationsEmpty(OfferDto inputOffer) {
        return inputOffer.publications().isEmpty();
    }


    private boolean isPubOfCorrectPublisher(OfferDto inputOffer) {
        return inputOffer.publications().stream()
                .map(publicationRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .noneMatch(p -> p.getPublisher().getUserId().equals(inputOffer.publisherId()))
                ;
    }


    private boolean validateInputOffer(OfferDto inputOffer){
        return  AreBuyerAndPublisherEquals(inputOffer)
                || isPublicationsEmpty(inputOffer)
                || isPubOfCorrectPublisher(inputOffer);
    }
}
