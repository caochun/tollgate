package com.example.tollgate.model;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class TollgateMessage {

    static final String TARGET_SERVICE = "target";
    static final String TARGET_DEFAULT = "#";

    private String target;

    public TollgateMessage(){
        this(TARGET_DEFAULT);
    }

    public TollgateMessage(String target){
        this.target = target;
    }

    public String getTarget(){
        return this.target;
    }

    public Message<? extends TollgateMessage> toMessage() {
        return MessageBuilder.<TollgateMessage>withPayload(this).setHeader(TARGET_SERVICE, getTarget()).build();
    }
}
