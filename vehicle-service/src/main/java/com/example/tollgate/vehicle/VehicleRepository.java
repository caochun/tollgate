package com.example.tollgate.vehicle;

import com.example.tollgate.model.Vehicle;
import com.example.tollgate.model.VehicleStateMachine;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class VehicleRepository {

    private Map<String, VehicleStateMachine> vehicleMap;

    public VehicleRepository() {
        this.vehicleMap = new HashMap<>();
    }

    public VehicleStateMachine findVehicleStateMachineByVehicleId(String vehicleId) {
        return vehicleMap.get(vehicleId);
    }

    public void saveVehicleStateMachine(VehicleStateMachine vehicleStateMachine) {
        vehicleMap.put(vehicleStateMachine.getVehicle().getId(), vehicleStateMachine);
    }

    public void saveVehicle(Vehicle vehicle){
        VehicleStateMachine vehicleStateMachine = vehicleMap.get(vehicle.getId());
        vehicleStateMachine.setVehicle(vehicle);
        vehicleMap.put(vehicle.getId(), vehicleStateMachine);
    }

    public List<VehicleStateMachine> findAll() {
        return vehicleMap.values().stream().collect(Collectors.toList());
    }

    public int vehicleCount() {
        return this.vehicleMap.size();
    }

    public void deleteVehicleStateMachineByVehicleId(String vehicleId) {
        this.vehicleMap.remove(vehicleId);
    }

}
