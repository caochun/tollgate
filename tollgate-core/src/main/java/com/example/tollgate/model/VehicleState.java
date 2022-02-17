package com.example.tollgate.model;

public class VehicleState extends TollgateMessage {

    public String stateId;

    public VehicleState(String stateId){
        this.stateId = stateId;
    }

}
