package com.example.tollgate.detecting;

import com.example.tollgate.channel.VehicleContext;
import com.example.tollgate.model.TollgateService;
import com.example.tollgate.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class DetectingService extends TollgateService {


    public boolean detect() {
        sendVehicleTransition(new Vehicle(),"start");
        return true;
    }

    @Override
    public void accept(VehicleContext context) {

    }
}
