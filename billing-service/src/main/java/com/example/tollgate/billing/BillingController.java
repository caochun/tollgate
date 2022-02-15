package com.example.tollgate.billing;


import com.example.tollgate.model.Billable;
import com.example.tollgate.model.Itinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {

    private BillingService billingService;

    @Autowired
    public void setBillingService(BillingService billingService){
        this.billingService = billingService;
    }

    @GetMapping("/billing")
    public ResponseEntity<Billable.Bill> getBill(@RequestBody Itinerary itinerary) {
        return new ResponseEntity<>(billingService.billing(itinerary), HttpStatus.OK);
    }

}