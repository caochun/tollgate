package com.example.tollgate.billing;

import com.example.tollgate.model.Billable;
import com.example.tollgate.model.Itinerary;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    public Billable.Bill billing(Itinerary itinerary) {
        return Billable.Bill.randomBill();
    }

}
