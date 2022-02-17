package com.example.tollgate.model;

public class State implements Transmittable {

    @Override
    public String getTarget() {
        return CHAR_WILDCARD;
    }

}
