package com.example.tollgate.model;

import java.util.ArrayList;
import java.util.List;

public class Itinerary extends TollgateEntity {

    private List<String> stops;

    public Itinerary(List<String> stops) {
        this.stops = stops;
    }

    public Itinerary() {
        this.stops = new ArrayList<>();
    }

    public void addItineraryStop(String stop) {
        if (this.stops == null) {
            this.stops = new ArrayList<>();
        }

        this.stops.add(stop);
    }

    public List<String> getItineraryStops() {
        return this.stops;
    }

}
