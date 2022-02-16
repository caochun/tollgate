package com.example.tollgate.recognizing;

import com.example.tollgate.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class RecognizingService {

    public String recognize(String data) {
        return Vehicle.randomVehicleId();
    }

}
