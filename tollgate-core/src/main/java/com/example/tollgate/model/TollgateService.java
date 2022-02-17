package com.example.tollgate.model;

import com.example.tollgate.channel.VehicleContext;

public interface TollgateService {
    public void accept(VehicleContext context);
}
