package com.example.tollgate.model;

import com.example.tollgate.channel.VehicleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;

public abstract class TollgateService {

    private String HEADER_KEY = "message-type";
    private String HEADER_VALUE_STATE_PREFIX = "state.";
    private String HEADER_VALUE_TRANSITION_PREFIX = "transition.";

    protected StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    protected void sendVehicleState(Vehicle vehicle, String state) {

        streamBridge.send(VehicleContext.BINDING_STATE,
                TollgateMessageBuilder.buildMessage(VehicleContext.generateVehicleState(vehicle, state),
                        HEADER_KEY,
                        HEADER_VALUE_STATE_PREFIX + state));
    }

    protected void sendVehicleTransition(Vehicle vehicle, String transition) {

        streamBridge.send(VehicleContext.BINDING_TRANSITION,
                TollgateMessageBuilder.buildMessage(VehicleContext.generateVehicleTransition(vehicle, transition),
                        HEADER_KEY,
                        HEADER_VALUE_TRANSITION_PREFIX + transition));
    }

    public abstract void accept(VehicleContext context);
}
