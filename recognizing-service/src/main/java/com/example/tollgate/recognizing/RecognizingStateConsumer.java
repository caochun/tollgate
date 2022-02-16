package com.example.tollgate.recognizing;

import com.example.tollgate.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("stateConsumer")
public class RecognizingStateConsumer implements Consumer<Message> {
    private RecognizingService recognizingService;

    @Autowired
    public void setRecognizingService(RecognizingService recognizingService) {
        this.recognizingService = recognizingService;
    }

    @Override
    public void accept(Message message) {
        if (message.getHeader().equals(Message.Header.STATUS_VEHICLE))
            recognizingService.recognize(message.getBody());
    }
}
