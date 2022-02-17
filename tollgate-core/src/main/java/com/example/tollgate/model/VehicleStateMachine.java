package com.example.tollgate.model;

import org.apache.commons.scxml2.env.AbstractStateMachine;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.TransitionTarget;

public class VehicleStateMachine extends AbstractStateMachine {

    public static final String SCXML_MODEL = "vehicle.xml";

    public VehicleStateMachine(Vehicle vehicle) throws ModelException {
        super(Vehicle.class.getClassLoader().getResource(SCXML_MODEL));
        this.vehicle = vehicle;
    }

    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean invoke(String methodName) {
        this.getLog().info(methodName);
        return true;
    }

    public boolean init() {
        return this.fireEvent("detects");
    }

    public Object[] getStates() {
        return this.getEngine().getStateMachine().getChildren().stream().map(TransitionTarget::getId).toArray();
    }

}
