package com.example.tollgate.vehicle;

import com.example.tollgate.model.VehicleStateMachine;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VehicleRepository {

    private Map<String, VehicleStateMachine> vehicleMap;

    public VehicleRepository() {
        this.vehicleMap = new HashMap<>();
    }

    public VehicleStateMachine findVehicleStateMachineByVehicleId(String vehicleId) {
        return vehicleMap.get(vehicleId);
    }

    public VehicleStateMachine saveVehicleStateMachine(VehicleStateMachine vehicleStateMachine) {
        return vehicleMap.put(vehicleStateMachine.getVehicle().getId(), vehicleStateMachine);
    }

    public int vehicleCount() {
        return this.vehicleMap.size();
    }

    public void deleteVehicleStateMachineByVehicleId(String vehicleId) {
        this.vehicleMap.remove(vehicleId);
    }

}
