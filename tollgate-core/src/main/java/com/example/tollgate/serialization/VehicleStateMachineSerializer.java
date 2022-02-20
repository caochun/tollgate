package com.example.tollgate.serialization;

import com.example.tollgate.model.VehicleStateMachine;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class VehicleStateMachineSerializer extends StdSerializer<VehicleStateMachine> {
    public VehicleStateMachineSerializer() {
        this(null);
    }

    public VehicleStateMachineSerializer(Class<VehicleStateMachine> t) {
        super(t);
    }

    @Override
    public void serialize(VehicleStateMachine vehicleStateMachine, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("vehicle", vehicleStateMachine.getVehicle());
        jsonGenerator.writeArrayFieldStart("currentStatus");
        Object[] states = vehicleStateMachine.getEngine().getCurrentStatus().getAllStates().stream().map(state-> state.getId()).toArray();
        jsonGenerator.writeArray(Arrays.stream(states).toArray(String[]::new), 0, states.length);
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
