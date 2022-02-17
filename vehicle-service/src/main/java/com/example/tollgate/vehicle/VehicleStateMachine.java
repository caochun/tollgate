package com.example.tollgate.vehicle;

import com.example.tollgate.binding.Delegate;
import com.example.tollgate.model.Entity;
import com.example.tollgate.model.Vehicle;
import com.example.tollgate.model.VehicleState;
import org.apache.commons.scxml2.SCXMLListener;
import org.apache.commons.scxml2.env.AbstractStateMachine;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.Transition;
import org.apache.commons.scxml2.model.TransitionTarget;

import java.util.UUID;

public class VehicleStateMachine extends AbstractStateMachine implements Entity {

    public static final String SCXML_MODEL = "vehicle.xml";


    private Delegate delegate;

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
        this.getEngine().addListener(this.getEngine().getStateMachine(), new SCXMLListener() {
            @Override
            public void onEntry(EnterableState enterableState) {
                VehicleStateMachine.this.delegate.send(new VehicleState(enterableState.getId()).toMessage());
            }

            @Override
            public void onExit(EnterableState enterableState) {

            }

            @Override
            public void onTransition(TransitionTarget transitionTarget, TransitionTarget transitionTarget1, Transition transition, String s) {

            }
        });
    }

    private String id;

    public VehicleStateMachine(Vehicle vehicle) throws ModelException {
        super(Vehicle.class.getClassLoader().getResource(SCXML_MODEL));
        this.id = UUID.randomUUID().toString();
        this.vehicle = vehicle;

    }

    private Vehicle vehicle;

    public Delegate getDelegate() {
        return delegate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean invoke(String methodName) {
        this.getLog().info(methodName);
        return true;
    }

    public Object[] getStates() {
        return this.getEngine().getStateMachine().getChildren().stream().map(TransitionTarget::getId).toArray();
    }

    @Override
    public String getId() {
        return this.id;
    }
}
