package com.example.tollgate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * This is the model for the whole tolling process
 */
@EqualsAndHashCode
public class Tolling extends TollgateEntity {

    @Getter
    private Instant startTime;

    @Getter
    private Instant endTime;

    public Tolling() {
        this.startTime = Instant.now();
        this.vehicle = new Vehicle();
        this.bill = new Bill();
        this.payment = new Payment();
        this.itinerary = new Itinerary();
    }

    public void end() {
        this.endTime = Instant.now();
    }

    @Getter
    @Setter
    private Vehicle vehicle;

    @Getter
    @Setter
    private Bill bill;

    @Getter
    @Setter
    private Payment payment;

    @Getter
    @Setter
    private Itinerary itinerary;

    @Override
    public String toString() {
        return "Tolling[" + this.getId() + "]";
    }

}
