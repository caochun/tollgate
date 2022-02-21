package com.example.tollgate.recognizing;

import com.example.tollgate.model.Tolling;
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

    @GetMapping("unconfirmed")
    public List<Tolling> unconfirmed() {
        return this.recognizingService.getUnconfirmedTollings();
    }

    @GetMapping("confirm")
    public void confirm(@RequestParam(name = "id") String tollingId) {
        this.recognizingService.confirm(tollingId);
    }

    @GetMapping("/unconfirm")
    public void unconfirm(@RequestParam(name = "id") String tollingId) {
        this.recognizingService.unconfirm(tollingId);
    }
}