package com.example.tollgate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public interface Billable {

    public Itinerary getItinerary();

    @Data
    @AllArgsConstructor
    public static class Bill {
        private double total;
        private String details;

        public static Bill randomBill() {
            return new Bill(new Random().nextInt(), RandomStringUtils.randomPrint(10, 100));
        }

    }
}
