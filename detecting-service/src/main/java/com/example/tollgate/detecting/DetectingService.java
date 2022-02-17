package com.example.tollgate.detecting;

import com.example.tollgate.model.MessageBuilder;
import com.example.tollgate.model.Vehicle;
import com.example.tollgate.channel.VehicleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class DetectingService {

    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public boolean detect() {
        streamBridge.send(VehicleContext.DESTINATION_TRANSITION,
                MessageBuilder.buildMessage(
                        VehicleContext.generateVehicleTransition(new Vehicle(), "start")));
        return true;
    }
}
