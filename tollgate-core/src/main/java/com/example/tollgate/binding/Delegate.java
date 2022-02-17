package com.example.tollgate.binding;

import com.example.tollgate.model.Topic;
import com.example.tollgate.model.Transmittable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Delegate {

    private Topic bindingTopic;

    public Delegate(Topic bindingTopic) {
        this.bindingTopic = bindingTopic;
    }


    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public boolean send(Message message) {
        return this.streamBridge.send(bindingTopic.value, message.toString());
    }

}
