package com.example.tollgate.billing;

import com.example.tollgate.model.Bill;
import com.example.tollgate.model.Itinerary;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    public Bill billing(Itinerary itinerary) {
        return Bill.randomBill();
    }

}
