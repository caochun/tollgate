package com.example.tollgate.recognizing;

import com.example.tollgate.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ResultRepository {

    private Map<String, RecognizingResult> results = new HashMap<>();

    public Collection<RecognizingResult> getAllResults() {
        return results.values();
    }

    public void saveResult(Vehicle vehicle) {
        this.results.put(vehicle.getId(), new RecognizingResult(vehicle, false));
    }

    public void saveResult(RecognizingResult recognizingResult) {
        this.results.put(recognizingResult.getVehicle().getId(), recognizingResult);
    }

    public RecognizingResult getResult(Vehicle vehicle) {
        return this.results.get(vehicle.getId());
    }

    public RecognizingResult getResultById(String vehicleId) {
        return this.results.get(vehicleId);
    }

    public RecognizingResult setConfirmResult(Vehicle vehicle, boolean confirm) {
        RecognizingResult r = this.getResult(vehicle);
        r.setConfirmed(confirm);
        this.saveResult(r);
        return r;
    }

    public RecognizingResult setConfirmResultById(String vehicleId, boolean confirm) {
        RecognizingResult r = this.getResultById(vehicleId);
        r.setConfirmed(confirm);
        this.saveResult(r);
        return r;
    }


}
