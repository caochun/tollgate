package com.example.tollgate.recognizing;

import com.example.tollgate.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RecognizingController {

    RecognizingService recognizingService;

    @Autowired
    public void setRecognizingService(RecognizingService recognizingService) {
        this.recognizingService = recognizingService;
    }

    @GetMapping("/unconfirmed")
    public List<Vehicle> unconfirmed() {
        return this.recognizingService.getUnconfirmedVehicles();
    }

    @GetMapping("/confirm")
    public void confirm(@RequestParam(name = "id") String vid) {
        this.recognizingService.confirm(vid);
    }

    @GetMapping("/unconfirm")
    public void unconfirm(@RequestParam(name = "id") String vid) {
        this.recognizingService.unconfirm(vid);
    }
}