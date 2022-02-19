package com.example.tollgate.channel;

import com.example.tollgate.model.TollgateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

public class VehicleContextConsumer implements Consumer<VehicleContext> {

    private TollgateService tollgateService;

    @Autowired
    public void setTollgateService(TollgateService tollgateService) {
        this.tollgateService = tollgateService;
    }


    @Override
    public void accept(VehicleContext vehicleContext) {
        tollgateService.accept(vehicleContext);
    }
}
