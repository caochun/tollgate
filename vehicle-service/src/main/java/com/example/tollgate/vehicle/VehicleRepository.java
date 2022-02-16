package com.example.tollgate.vehicle;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VehicleRepository {

    private Map<String, Vehicle> vehicleMap;

    public VehicleRepository() {
        this.vehicleMap = new HashMap<>();
    }

    public Vehicle findVehicle(String vehicleId) {
        return vehicleMap.get(vehicleId);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleMap.put(vehicle.getVehicleId(), vehicle);
    }

    public void deleteVehicle(String vehicleId) {
        this.vehicleMap.remove(vehicleId);
    }

}
