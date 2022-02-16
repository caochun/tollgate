package com.example.tollgate.model;

import java.time.Instant;

public class Message {

    public enum Header {
        STATUS_VEHICLE, COMMAND_VEHICLE, DETECT_VEHICLE
    }

    private String target;
    private Header header;
    private String body;
    private long timestamp;

    public String getTarget() {
        return this.target;
    }

    public Header getHeader() {
        return this.header;
    }

    public String getBody() {
        return this.body;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Message() {

    }

    public Message(String target, Header header, String body) {
        this.target = target;
        this.header = header;
        this.body = body;
        timestamp = Instant.now().getEpochSecond();
    }

}
