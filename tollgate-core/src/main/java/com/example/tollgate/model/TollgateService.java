package com.example.tollgate.model;

import com.example.tollgate.channel.TollingBinding;
import com.example.tollgate.channel.TollingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;

public abstract class TollgateService {

    private String HEADER_KEY = "message-type";
    private String HEADER_VALUE_STATE_PREFIX = "state.";
    private String HEADER_VALUE_TRANSITION_PREFIX = "transition.";

    protected StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    protected void sendTollingState(Tolling tolling, String state) {

        streamBridge.send(TollingBinding.BINDING_STATE,
                TollgateMessageBuilder.buildMessage(TollingContext.generateTollingState(tolling, state),
                        HEADER_KEY,
                        HEADER_VALUE_STATE_PREFIX + state));
    }

    protected void sendTollingTransition(Tolling tolling, String transition) {

        streamBridge.send(TollingBinding.BINDING_TRANSITION,
                TollgateMessageBuilder.buildMessage(TollingContext.generateTollingTransition(tolling, transition),
                        HEADER_KEY,
                        HEADER_VALUE_TRANSITION_PREFIX + transition));
    }

    public abstract void accept(TollingContext context);
}
