package com.example.tollgate.binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class StateDelegate {

    private final String BINDING_STATE_OUTPUT = "state-output";

    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void onState(String t) {
        streamBridge.send(BINDING_STATE_OUTPUT, t);
    }
}
