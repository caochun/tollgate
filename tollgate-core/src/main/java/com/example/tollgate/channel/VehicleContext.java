package com.example.tollgate.channel;

import com.example.tollgate.model.TollgateEntity;
import com.example.tollgate.model.Vehicle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleContext extends TollgateEntity {


    public static final String BINDING_TRANSITION = "vehicle-transition";
    public static final String BINDING_STATE = "vehicle-state";


    private String context;

    /**
     * true if context is vehicle's current state,
     * otherwise it's a happening transition
     */
    private boolean isState = false;

    private Vehicle vehicle;


    private VehicleContext(Vehicle vehicle, String context, boolean isState) {
        super();
        this.context = context;
        this.vehicle = vehicle;
        this.isState = isState;
    }

    public static VehicleContext generateVehicleState(Vehicle vehicle, String state) {
        return new VehicleContext(vehicle, state, true);
    }

    public static VehicleContext generateVehicleTransition(Vehicle vehicle, String transition) {
        return new VehicleContext(vehicle, transition, false);
    }

}
