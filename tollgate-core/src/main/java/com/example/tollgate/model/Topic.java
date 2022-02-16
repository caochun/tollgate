package com.example.tollgate.model;

public enum Topic {
    STATE("topic-state"), COMMAND("topic-command");

    public final String value;

    Topic(String value) {
        this.value = value;

    }
}
