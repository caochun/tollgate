package com.example.tollgate.model;

import java.time.Instant;

public class HeartBeat extends TollgateMessage {


    private String timestamp = Instant.now().toString();

    public HeartBeat(String target) {
        super(target);
    }

    public String getTimestamp() {
        return this.timestamp;
    }

}
