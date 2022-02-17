package com.example.tollgate.model;

import java.util.UUID;

public class TollgateEntity {

    private String id;

    public String getId() {
        return id;
    }

    public TollgateEntity(String id) {
        this.id = id;
    }

    public TollgateEntity(){
        this.id = UUID.randomUUID().toString();
    }
}
