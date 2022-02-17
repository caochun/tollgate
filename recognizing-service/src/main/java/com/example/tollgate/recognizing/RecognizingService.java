package com.example.tollgate.recognizing;

import com.example.tollgate.channel.VehicleContext;
import com.example.tollgate.model.MessageBuilder;
import com.example.tollgate.model.TollgateService;
import com.example.tollgate.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class RecognizingService implements TollgateService {

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

    public Vehicle[] getUnconfirmedVehicles() {
        Object[] objects = this.resultRepository.getAllResults().stream().filter(r -> !r.isConfirmed()).toArray();
        Vehicle[] v = new Vehicle[objects.length];

        for (int i = 0; i < v.length; i++) {
            v[i] = (Vehicle) objects[i];
        }
        return v;
    }

    public void confirm(Vehicle vehicle) {
        this.resultRepository.setConfirmResult(vehicle, true);
        streamBridge.send(VehicleContext.DESTINATION_TRANSITION,
                MessageBuilder.buildMessage(
                        VehicleContext.generateVehicleTransition(vehicle, "recognition_successes")));
    }

    public void confirm(String vehicleId) {
        RecognizingResult r = this.resultRepository.setConfirmResultById(vehicleId, true);
        streamBridge.send(VehicleContext.DESTINATION_TRANSITION,
                MessageBuilder.buildMessage(
                        VehicleContext.generateVehicleTransition(r.getVehicle(), "recognition_successes")));
    }

    public void unconfirm(String vehicleId) {
        RecognizingResult r = this.resultRepository.setConfirmResultById(vehicleId, false);
        streamBridge.send(VehicleContext.DESTINATION_TRANSITION,
                MessageBuilder.buildMessage(
                        VehicleContext.generateVehicleTransition(r.getVehicle(), "recognition_fails")));
    }


    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


    @Override
    public void accept(VehicleContext context) {

        if (context.isState()) { //actually it must be

            this.recognize(context.getVehicle());


        } else {
            //otherwise, just ignore
        }

    }
}
