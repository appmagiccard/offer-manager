package com.magicauction.offermanager.controller;

import com.magicauction.offermanager.entity.dtos.OfferDto;
import com.magicauction.offermanager.entity.exceptions.UserNotFoundException;
import com.magicauction.offermanager.proccesor.IOfferProcessor;
import com.magicauction.offermanager.proccesor.OfferProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("offer")
public class OfferController {

    private static final Logger log = LoggerFactory.getLogger(OfferController.class);
    private final IOfferProcessor offerProcessor;

    @Autowired
    public OfferController(OfferProcessor offerProcessor) {
        this.offerProcessor = offerProcessor;
    }

    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<List<OfferDto>> getAllOffersByPublisherId(@PathVariable Long publisherId){
        return ResponseEntity.ok(offerProcessor.findOffersByPublisher(publisherId));
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OfferDto>> getAllOffersByBuyerId(@PathVariable Long buyerId){
        return ResponseEntity.ok(offerProcessor.findOffersByBuyer(buyerId));
    }

    @GetMapping("/publisher/{publisherId}/buyer/{buyerId}")
    public ResponseEntity<List<OfferDto>> getAllOffersByPublisherAndBuyerId(@PathVariable Long publisherId, @PathVariable Long buyerId){
        return ResponseEntity.ok(offerProcessor.findOfferByPublisherIdAndByBuyerId(publisherId, buyerId));
    }

    @PutMapping("/status/finished")
    public ResponseEntity<List<OfferDto>> finishOffersByIds(@RequestBody List<Long> idsToUpdate){
        return ResponseEntity.ok(offerProcessor.updateOffersToFinished(idsToUpdate));
    }

    //BASIC CRUD
    @GetMapping("/{offerId}")
    public ResponseEntity<OfferDto> getOfferByOfferId(@PathVariable Long offerId){
        logName("getOfferById");
        return offerProcessor.findOfferById(offerId)
                .map(ResponseEntity::ok)
                .orElseGet(this::notFoundResponse)
                ;
    }

    @GetMapping
    public ResponseEntity<List<OfferDto>> getAllOffers(){
        logName("GetAllOffers");
        return ResponseEntity.ok(offerProcessor.findAllOffers());
    }

    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody OfferDto inputOffer) throws UserNotFoundException {
        logName("createOffer");
        return offerProcessor.createNewOffer(inputOffer)
                .map(ResponseEntity::ok)
                .orElseGet(this::notFoundResponse)
                ;
    }


    @PutMapping("/{offerId}")
    public ResponseEntity<OfferDto> updateOfferById(@PathVariable Long offerId,@RequestBody OfferDto inputOffer) throws UserNotFoundException {
        return offerProcessor.updateOffer(offerId, inputOffer)
                .map(ResponseEntity::ok)
                .orElseGet(this::notFoundResponse)
                ;
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<Boolean> deleteOfferById(@PathVariable Long offerId){
        return ResponseEntity.ok(offerProcessor.deleteOfferById(offerId));
    }

    private void logName(String methodName) {
        log.info("called {}", methodName);
    }
    private ResponseEntity<OfferDto> notFoundResponse() {
        return ResponseEntity.status(404).build();
    }

}
