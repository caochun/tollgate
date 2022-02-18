package com.example.tollgate.recognizing;

import com.example.tollgate.channel.VehicleContext;
import com.example.tollgate.model.TollgateMessageBuilder;
import com.example.tollgate.model.TollgateService;
import com.example.tollgate.model.Vehicle;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecognizingService extends TollgateService {

    private ResultRepository resultRepository;

    @Autowired
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Vehicle recognize(Vehicle vehicle) {
        vehicle.setRandomPlate();
        resultRepository.saveResult(vehicle);
        return vehicle;
    }

    public List<Vehicle> getUnconfirmedVehicles() {
        return this.resultRepository.getAllResults().stream()
                .filter(r -> !r.isConfirmed())
                .map(r -> r.getVehicle())
                .collect(Collectors.toList());
    }

    public void confirm(Vehicle vehicle) {
        this.resultRepository.setConfirmResult(vehicle, true);
        this.sendVehicleTransition(vehicle,"recognition_successes");
    }

    public void confirm(String vehicleId) {
        RecognizingResult r = this.resultRepository.getResultById(vehicleId);
        this.confirm(r.getVehicle());
    }

    public void unconfirm(Vehicle vehicle){
        this.resultRepository.setConfirmResult(vehicle, false);
        this.sendVehicleTransition(vehicle,"recognition_fails");
    }

    public void unconfirm(String vehicleId) {
        RecognizingResult r = this.resultRepository.getResultById(vehicleId);
       this.unconfirm(r.getVehicle());
    }

    @Override
    public void accept(VehicleContext context) {

        if (context.isState()) { //actually it must be
            LogFactory.getLog(RecognizingService.class).info("handling vehicle " + context.getVehicle().getId() + " at state " + context.getContext());
            this.recognize(context.getVehicle());
        } else {
            //otherwise, just ignore
        }

    }
}
