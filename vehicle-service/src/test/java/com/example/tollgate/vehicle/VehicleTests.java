package com.example.tollgate.vehicle;

import org.apache.commons.scxml2.model.ModelException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class VehicleTests {

    @Test
    public void testVehicleModel() throws ModelException {
        Vehicle v = new Vehicle("1");
        Arrays.stream(v.getStates()).forEach(System.out::println);
        assert(v.getStates()!=null);
    }
}
