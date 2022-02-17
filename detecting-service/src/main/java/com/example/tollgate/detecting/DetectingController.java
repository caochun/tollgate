package com.example.tollgate.detecting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetectingController {

    private DetectingService detectingService;

    @Autowired
    public void setDetectingService(DetectingService detectingService) {
        this.detectingService = detectingService;
    }

    @GetMapping("detect")
    public String detect() {
        this.detectingService.detect();
        return "OK";
    }
}
