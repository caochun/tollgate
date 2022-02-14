package com.example.tollgate.binding;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("stateConsumer")
public class StateAdaptor<T> implements Consumer<String> {

    private T stateOwner;


    public void setStateOwner(T stateOwner) {
        this.stateOwner = stateOwner;
    }

    @Override
    public void accept(String s) {
        if (this.stateOwner != null) {
//            this.stopWatchPanel.updateStatus(s);
        }
    }
}
