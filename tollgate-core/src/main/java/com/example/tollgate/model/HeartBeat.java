package com.example.tollgate.model;

import java.time.Instant;

public class HeartBeat implements Transmittable {


    private String timestamp = Instant.now().toString();

    private String target;

    public HeartBeat(String target) {
        this.target = target;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public HeartBeat() {
        this(CHAR_WILDCARD);
    }

    @Override
    public String getTarget() {
        return this.target;
    }
}
