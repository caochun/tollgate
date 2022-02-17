package com.example.tollgate.vehicle;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("eventConsumer")
public class VehicleEventConsumer implements Consumer<Message> {

    private VehicleService vehicleService;

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public void accept(Message message) {
//        if (message.getHeader().equals(Messaging.Header.COMMAND_VEHICLE))
//            vehicleService.deliverMessage(message);
//        else if (message.getHeader().equals(Messaging.Header.STATUS_VEHICLE))
//            vehicleService.registerVehicle();
    }

}
