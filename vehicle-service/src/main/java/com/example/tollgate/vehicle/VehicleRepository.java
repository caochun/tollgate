package com.example.tollgate.vehicle;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VehicleRepository {

    private Map<String, VehicleStateMachine> vehicleMap;

    public VehicleRepository() {
        this.vehicleMap = new HashMap<>();
    }

    public VehicleStateMachine findVehicle(String vehicleId) {
        return vehicleMap.get(vehicleId);
    }

    public VehicleStateMachine saveVehicle(VehicleStateMachine vehicle) {
        return vehicleMap.put(vehicle.getId(), vehicle);
    }

    public int vehicleCount(){
        return this.vehicleMap.size();
    }

    public void deleteVehicle(String vehicleId) {
        this.vehicleMap.remove(vehicleId);
    }

}
