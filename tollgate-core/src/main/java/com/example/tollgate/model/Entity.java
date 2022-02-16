package com.example.tollgate.model;


public interface Entity {
    public static final Entity ANY = new Entity() {
        @Override
        public String getId() {
            return "*";
        }
    };

    public String getId();
}
