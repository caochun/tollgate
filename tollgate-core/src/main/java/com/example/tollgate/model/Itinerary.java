package com.example.tollgate.model;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {

    public class ItineraryStop {

    }

    private List<ItineraryStop> stops;

    public Itinerary(List<ItineraryStop> stops) {
        this.stops = stops;
    }

    public Itinerary() {
        this.stops = new ArrayList<>();
    }

    public void addItineraryStop(ItineraryStop stop) {
        if (this.stops == null) {
            this.stops = new ArrayList<>();
        }

        this.stops.add(stop);
    }

    public List<ItineraryStop> getItineraryStops() {
        return this.stops;
    }

}
