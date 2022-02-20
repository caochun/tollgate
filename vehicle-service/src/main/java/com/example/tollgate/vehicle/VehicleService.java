package com.example.tollgate.vehicle;

import com.example.tollgate.channel.VehicleContext;
import com.example.tollgate.model.TollgateService;
import com.example.tollgate.model.Vehicle;
import com.example.tollgate.model.VehicleStateMachine;
import org.apache.commons.scxml2.SCXMLListener;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.Transition;
import org.apache.commons.scxml2.model.TransitionTarget;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService extends TollgateService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    public void registerVehicle(Vehicle vehicle) {

        VehicleStateMachine v;

        try {
            v = new VehicleStateMachine(vehicle);
        } catch (ModelException e) {
            e.printStackTrace();
            return;
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

        this.vehicleRepository.saveVehicleStateMachine(v);

    }

    public void registerVehicle() {
        this.registerVehicle(new Vehicle());
    }

    public boolean start(Vehicle vehicle) {
        return this.start(vehicle.getId());
    }

    public boolean start(String vehicleId) {
        VehicleStateMachine vsm = vehicleRepository.findVehicleStateMachineByVehicleId(vehicleId);
        if (vsm != null) {
            return vsm.fireEvent("start");
        }
        return false;
    }

    public List<VehicleStateMachine> vehicles() {
        return this.vehicleRepository.findAll();
    }


    @Override
    public void accept(VehicleContext context) {
        if (!context.isState()) {
            VehicleStateMachine vsm = vehicleRepository.findVehicleStateMachineByVehicleId(context.getVehicle().getId());
            if (vsm != null) {

                vsm.fireEvent(context.getContext());

                if (context.getContext().equals("recognition_successes")) {
                    vehicleRepository.saveVehicle(context.getVehicle());
                }
            }
        } else {
            //otherwise, just ignore
        }
    }

}
