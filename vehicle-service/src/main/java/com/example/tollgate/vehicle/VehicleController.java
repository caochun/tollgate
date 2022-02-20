package com.example.tollgate.vehicle;

import com.example.tollgate.model.Vehicle;
import com.example.tollgate.model.VehicleStateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleController {

    VehicleService vehicleService;

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("vehicles")
    public ResponseEntity<List<VehicleStateMachine>> vehicleCount() {
        return new ResponseEntity<>(this.vehicleService.vehicles(), HttpStatus.OK);
    }

    @GetMapping("/register")
    public ResponseEntity<String> register() {
        this.vehicleService.registerVehicle();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/start")
    public ResponseEntity<String> start(@RequestParam(name = "id") String vid) {
        if (this.vehicleService.start(vid))
            return ResponseEntity.ok("OK");
        else
            return ResponseEntity.badRequest().build();
    }
}
