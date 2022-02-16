package com.example.tollgate.billing;

import com.example.tollgate.model.Itinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("itineraryConsumer")
public class ItineraryConsumer implements Consumer<Itinerary> {

    private BillingService billingService;

    @Autowired
    public void setBillingService(BillingService billingService){
        this.billingService = billingService;
    }

    @Override
    public void accept(Itinerary itinerary) {
        this.billingService.billing(itinerary);
    }
}
