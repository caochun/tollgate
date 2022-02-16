package com.example.tollgate.detecting;

import com.example.tollgate.model.Entity;
import com.example.tollgate.model.Message;
import com.example.tollgate.model.Vehicle;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component("vehicleDetector")
public class VehicleDetector implements Supplier<Message> {

    @Override
    public Message get() {
        return new Message(Entity.ANY.getId(), Message.Header.DETECT_VEHICLE, Vehicle.PLATE_HOLDER );
    }

}
