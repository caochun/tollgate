package com.example.tollgate.model;


import lombok.Data;

public interface Billable {

    public Itinerary getItinerary();

    @Data
    public static class Bill{
        private double total;
        private String details;
    }
}
