package com.example.tollgate.model;

import org.springframework.messaging.Message;

public class MessageBuilder {

    static final String ROUTING_KEY_EXPRESSION_STATE="'header.state'";
    static final String TARGET_DEFAULT = "#";

    public static Message<? extends TollgateEntity> buildMessage(TollgateEntity entity, String target) {
        return org.springframework.messaging.support.MessageBuilder.withPayload(entity).setHeader(ROUTING_KEY_EXPRESSION_STATE, target).build();
    }

    public static Message<? extends TollgateEntity> buildMessage(TollgateEntity entity) {
        return MessageBuilder.buildMessage(entity, TARGET_DEFAULT);
    }
}
