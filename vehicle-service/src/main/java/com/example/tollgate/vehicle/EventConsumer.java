package com.example.tollgate.vehicle;

import com.example.tollgate.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("eventConsumer")
public class EventConsumer implements Consumer<Message> {

    private VehicleService vehicleService;

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public void accept(Message message) {
        if (message.getHeader().equals(Message.Header.COMMAND_VEHICLE))
            vehicleService.deliverMessage(message);
    }

}
