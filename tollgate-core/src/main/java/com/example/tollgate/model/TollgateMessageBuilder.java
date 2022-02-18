package com.example.tollgate.model;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Instant;

public class TollgateMessageBuilder {

    public static Message<String> buildHeartBeat(String origin) {
        return MessageBuilder.withPayload(origin + "'s heartBeat at " + Instant.now().toString()).build();
    }

    public static Message<? extends TollgateEntity> buildMessage(TollgateEntity entity, String headerKey, String headerValue) {
        return MessageBuilder.withPayload(entity).setHeader(headerKey, headerValue).build();
    }
}
