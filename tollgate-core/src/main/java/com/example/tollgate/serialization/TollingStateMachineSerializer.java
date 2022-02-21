package com.example.tollgate.serialization;

import com.example.tollgate.statemachine.TollingStateMachine;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Arrays;

public class TollingStateMachineSerializer extends StdSerializer<TollingStateMachine> {
    public TollingStateMachineSerializer() {
        this(null);
    }

    public TollingStateMachineSerializer(Class<TollingStateMachine> t) {
        super(t);
    }

    @Override
    public void serialize(TollingStateMachine tollingStateMachine, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("tolling", tollingStateMachine.getTolling());
        jsonGenerator.writeArrayFieldStart("currentStatus");
        Object[] states = tollingStateMachine.getEngine().getCurrentStatus().getAllStates().stream().map(state -> state.getId()).toArray();
        jsonGenerator.writeArray(Arrays.stream(states).toArray(String[]::new), 0, states.length);
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
