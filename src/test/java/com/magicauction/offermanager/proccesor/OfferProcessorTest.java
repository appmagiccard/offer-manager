package com.magicauction.offermanager.proccesor;

import com.magicauction.offermanager.entity.Offer;
import com.magicauction.offermanager.entity.Publication;
import com.magicauction.offermanager.entity.TradeStatus;
import com.magicauction.offermanager.entity.User;
import com.magicauction.offermanager.entity.dtos.OfferDto;
import com.magicauction.offermanager.entity.exceptions.UserNotFoundException;
import com.magicauction.offermanager.entity.repository.OfferRepository;
import com.magicauction.offermanager.entity.repository.PublicationRepository;
import com.magicauction.offermanager.entity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferProcessorTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PublicationRepository publicationRepository;

    private OfferProcessor offerProcessor;

    @BeforeEach
    void setUp() {
        offerProcessor = new OfferProcessor(offerRepository, userRepository, publicationRepository);
    }

    @Test void findOfferById_whenIsFound(){
        Long id = 1L;
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer()));
        Optional<OfferDto> offerById = offerProcessor.findOfferById(id);
        assertNotNull(offerById);
        assertTrue(offerById.isPresent());
    }



    @Test void findOfferById_whenIsNotFound(){
        Long id = 1L;
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<OfferDto> offerById = offerProcessor.findOfferById(id);
        assertNotNull(offerById);
        assertTrue(offerById.isEmpty());

    }

    @Test void findOfferById_whenIsNotOk(){
        Long id = 1L;
        when(offerRepository.findById(anyLong())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> offerProcessor.findOfferById(id));
    }

    @Test void findAllOffers_whenIsFound(){
        when(offerRepository.findAll()).thenReturn(offers());
        List<OfferDto> allOffers = offerProcessor.findAllOffers();
        assertNotNull(allOffers);
        assertFalse(allOffers.isEmpty());
    }

    @Test void findAllOffers_whenIsNotFound(){
        when(offerRepository.findAll()).thenReturn(new ArrayList<>());
        List<OfferDto> allOffers = offerProcessor.findAllOffers();
        assertNotNull(allOffers);
        assertTrue(allOffers.isEmpty());
    }
    @Test void findAllOffers_whenIsNotOK(){
        when(offerRepository.findAll()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> offerProcessor.findAllOffers());
    }

    @Test void createNewOffer_whenIsOk() throws UserNotFoundException {
        long user1 = 1L;
        long user2 = 2L;
        when(userRepository.findById(user1)).thenReturn(Optional.of(user(user1)));
        when(userRepository.findById(user2)).thenReturn(Optional.of(user(user2)));
        when(publicationRepository.findAllById(anyList())).thenReturn(publications());
        when(offerRepository.save(any())).thenReturn(offer());
        Optional<OfferDto> newOffer = offerProcessor.createNewOffer(inputOffer());
        assertNotNull(newOffer);
        assertTrue(newOffer.isPresent());
    }
    @Test void createNewOffer_whenValidationError() throws UserNotFoundException {
        Optional<OfferDto> newOffer = offerProcessor.createNewOffer(inputOfferWithUserError());
        assertNotNull(newOffer);
        assertTrue(newOffer.isEmpty());
    }
    @Test void createNewOffer_whenUserNotFound() throws UserNotFoundException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> offerProcessor.createNewOffer(inputOffer()));
    }
    @Test void createNewOffer_whenUserPublicationsListIsEmpty() throws UserNotFoundException {
        Optional<OfferDto> newOffer = offerProcessor.createNewOffer(inputOfferWithEmptyList());
        assertNotNull(newOffer);
        assertTrue(newOffer.isEmpty());
    }

    private OfferDto inputOfferWithEmptyList() {
        return new OfferDto(
                1L,
                2L,
                Collections.EMPTY_LIST
        );
    }

    @Test void updateOffer_whenIsOk(){
        long user1 = 1L;
        long user2 = 2L;
        long offerId = 34L;
        when(userRepository.findById(user1)).thenReturn(Optional.of(user(user1)));
        when(userRepository.findById(user2)).thenReturn(Optional.of(user(user2)));
        //TODO:REVISAR when(publicationRepository.findAllById(anyList())).thenReturn(publications());
        when(offerRepository.save(any())).thenReturn(offer());
        Optional<OfferDto> newOffer = offerProcessor.updateOffer(offerId,inputOfferWithUpdate());
        assertNotNull(newOffer);
        assertTrue(newOffer.isPresent());
    }

    private OfferDto inputOfferWithUpdate() {
        return new OfferDto(
                1L,
                2L,
                Arrays.asList(1L, 2L)
        );
    }

    @Test void updateOffer_whenValidationError(){}
    @Test void updateOffer_whenOfferNotFound(){}
    @Test void updateOffer_whenUserNotFound(){}


    private OfferDto inputOfferWithUserError() {
        return new OfferDto(
                1L,
                1L,
                Collections.singletonList(1L)
        );
    }

    private OfferDto inputOffer() {
        return new OfferDto(
                1L,
                2L,
                Collections.singletonList(1L)
        );
    }

    private List<Publication> publications() {
        return Collections.singletonList(new Publication(1L, user(1L), null));
    }

    @Test void createNewOffer_whenIsNotOk(){}

    private static Offer offer() {
        return offer(1L);
    }

    private List<Offer> offers() {
        return Arrays.asList(offer(1L), offer(2L), offer(3L));
    }
    private static Offer offer(Long offerId) {
        Offer o = new Offer();
        o.setOfferId(offerId);
        o.setBuyer(buyer());
        o.setFinishedAt(null);
        o.setPublisher(publisher());
        o.setPublications(new ArrayList<>());
        o.setStatus(TradeStatus.STARTED);
        o.setCreatedAt(new Date(new java.util.Date().getTime()));
        return o;
    }

    private static User publisher() {
        return user(2L);
    }

    private static User buyer() {
        return user(1L);
    }

    private static User user(long l) {
        User u = new User();
        u.setUserId(l);
        u.setName("7S3R");
        return u;
    }
}