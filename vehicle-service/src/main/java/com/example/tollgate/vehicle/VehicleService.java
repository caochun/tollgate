package com.example.tollgate.vehicle;

import com.example.tollgate.channel.VehicleContext;
import com.example.tollgate.model.*;
import org.apache.commons.scxml2.SCXMLListener;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.Transition;
import org.apache.commons.scxml2.model.TransitionTarget;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService extends TollgateService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleStateMachine registerVehicle(Vehicle vehicle) {

        VehicleStateMachine v;

        try {
            v = new VehicleStateMachine(vehicle);
        } catch (ModelException e) {
            e.printStackTrace();
            return null;
        }

        v.getEngine().addListener(v.getEngine().getStateMachine(), new SCXMLListener() {
            @Override
            public void onEntry(EnterableState enterableState) {
                LoggerFactory.getLogger(VehicleService.class).info(v.getVehicle().getId() + " in state: " + enterableState.getId());
                VehicleService.this.sendVehicleState(v.getVehicle(), enterableState.getId());
            }

            @Override
            public void onExit(EnterableState enterableState) {

            }

            @Override
            public void onTransition(TransitionTarget transitionTarget, TransitionTarget transitionTarget1, Transition transition, String s) {
                LoggerFactory.getLogger(VehicleService.class).info(v.getVehicle().getId() + " transition fired: " + s);
            }
        });
        v.init();
        return this.vehicleRepository.saveVehicleStateMachine(v);

    }

    @Override
    public void accept(VehicleContext context) {
        if (!context.isState()) {
            if (context.getContext().equals("start")) {    //we currently treat vehicle detection in an ad hoc manner
                this.registerVehicle(context.getVehicle());
            } else {
                VehicleStateMachine vsm = vehicleRepository.findVehicleStateMachineByVehicleId(context.getVehicle().getId());
                if (vsm != null) {
                    vsm.fireEvent(context.getContext());
                }
            }
        } else {
            //otherwise, just ignore
        }


    }

    public int count() {
        return this.vehicleRepository.vehicleCount();
    }
}
