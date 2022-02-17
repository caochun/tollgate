package com.example.tollgate.model;

import java.time.Instant;

public class HeartBeat extends TollgateEntity {

    private String timestamp = Instant.now().toString();

    public String getTimestamp() {
        return this.timestamp;
    }

}
