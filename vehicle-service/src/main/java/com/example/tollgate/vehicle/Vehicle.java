package com.example.tollgate.vehicle;

import com.example.tollgate.model.StatedEntity;
import org.apache.commons.scxml2.env.AbstractStateMachine;
import org.apache.commons.scxml2.model.ModelException;

public class Vehicle extends AbstractStateMachine implements StatedEntity {


    public Vehicle() throws ModelException {
        super(Vehicle.class.getClassLoader().getResource("vehicle.xml"));
    }

    @Override
    public String getState() {
        return null;
    }
}
