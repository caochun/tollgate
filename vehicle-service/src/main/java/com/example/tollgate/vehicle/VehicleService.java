package com.example.tollgate.vehicle;

import com.example.tollgate.binding.Delegate;
import com.example.tollgate.model.Entity;
import com.example.tollgate.model.Message;
import com.example.tollgate.model.Topic;
import org.apache.commons.scxml2.model.ModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void deliverMessage(Message message) {
        Vehicle v = vehicleRepository.findVehicle(message.getTarget());
        if (v != null) v.fireEvent(message.getBody());
    }

    public Vehicle registerVehicle(String vehicleId) {
        try {
            Vehicle v = new Vehicle(vehicleId);
            v.setDelegate(delegate);
            return vehicleRepository.saveVehicle(v);
        } catch (ModelException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Delegate delegate = new Delegate(Topic.STATE);
}
