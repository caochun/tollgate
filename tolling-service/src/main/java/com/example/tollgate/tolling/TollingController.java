package com.example.tollgate.tolling;

import com.example.tollgate.statemachine.TollingStateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TollingController {

    TollingService tollingService;

    @Autowired
    public void setTollingService(TollingService vehicleService) {
        this.tollingService = vehicleService;
    }

    @GetMapping("tollings")
    public ResponseEntity<List<TollingStateMachine>> tollings() {
        return new ResponseEntity<>(this.tollingService.tollings(), HttpStatus.OK);
    }

    @GetMapping("register")
    public ResponseEntity<String> register() {
        this.tollingService.newTolling();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("start")
    public ResponseEntity<String> start(@RequestParam(name = "id") String tid) {
        if (this.tollingService.startTolling(tid))
            return ResponseEntity.ok("OK");
        else
            return ResponseEntity.badRequest().build();
    }
}
