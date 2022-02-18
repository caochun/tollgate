package com.example.tollgate.vehicle;

import com.example.tollgate.channel.VehicleContext;
import com.example.tollgate.model.*;
import org.apache.commons.scxml2.SCXMLListener;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.Transition;
import org.apache.commons.scxml2.model.TransitionTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class VehicleService implements TollgateService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
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
                streamBridge.send(VehicleContext.DESTINATION_STATE,
                        MessageBuilder.buildMessage(
                                VehicleContext.generateVehicleState(v.getVehicle(), "state."+enterableState.getId()))
                );
            }

            @Override
            public void onExit(EnterableState enterableState) {

            }

            @Override
            public void onTransition(TransitionTarget transitionTarget, TransitionTarget transitionTarget1, Transition transition, String s) {

            }
        });
        v.init();
        return this.vehicleRepository.saveVehicleStateMachine(v);

    }

    @Override
    public void accept(VehicleContext context) {
        if (!context.isState()) {
            if (context.getContext().equals("detects")) {    //we currently treat vehicle detection in an ad hoc manner
                this.registerVehicle(context.getVehicle());
            } else {
                vehicleRepository.findVehicleStateMachineByVehicleId(context.getVehicle().getId()).fireEvent(context.getContext());
            }
        } else {
            //otherwise, just ignore
        }


    }

    public int count() {
        return this.vehicleRepository.vehicleCount();
    }
}
