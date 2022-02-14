package com.example.tollgate.binding;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("onEvent")
public class EventAdaptor<T> implements Consumer<String> {

    private T stopWatch;

    public void setStopWatch(T stopWatch) {
        this.stopWatch = stopWatch;
    }

    @Override
    public void accept(String s) {
//        System.out.println("eventAdaptor");
        if (this.stopWatch != null) {
//            this.stopWatch.fireEvent(s);
        }
    }
}
