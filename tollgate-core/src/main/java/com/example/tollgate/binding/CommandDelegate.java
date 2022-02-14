package com.example.tollgate.binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class CommandDelegate {
    private final String BINDING_EVENT_OUT = "event-out";

    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendCommand(String command) {
        this.streamBridge.send(BINDING_EVENT_OUT, command);
    }

}
