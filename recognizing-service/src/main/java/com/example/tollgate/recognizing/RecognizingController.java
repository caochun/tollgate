package com.example.tollgate.recognizing;

import com.example.tollgate.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RecognizingController {

    RecognizingService recognizingService;

    @Autowired
    public void setRecognizingService(RecognizingService recognizingService) {
        this.recognizingService = recognizingService;
    }

    public List<Vehicle> unconfirmed() {
        return this.recognizingService.getUnconfirmedVehicles();

    }
}
