package com.example.tollgate.model;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public interface Transmittable {

    static final String TARGET_SERVICE = "target";
    static final String CHAR_WILDCARD = "*";

    public String getTarget();

    public default Message toMessage() {
        return MessageBuilder.withPayload(this).setHeader(TARGET_SERVICE, getTarget()).build();
    }
}
