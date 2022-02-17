package com.example.tollgate.model;

import org.springframework.messaging.Message;

public class MessageBuilder {

    static final String TARGET_SERVICE = "target";
    static final String TARGET_DEFAULT = "#";

    public static Message<? extends TollgateEntity> buildMessage(TollgateEntity entity, String target) {
        return org.springframework.messaging.support.MessageBuilder.withPayload(entity).setHeader(TARGET_SERVICE, target).build();
    }

    public static Message<? extends TollgateEntity> buildMessage(TollgateEntity entity) {
        return MessageBuilder.buildMessage(entity, TARGET_DEFAULT);
    }
}
