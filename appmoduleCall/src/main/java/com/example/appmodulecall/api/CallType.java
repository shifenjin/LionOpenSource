package com.example.appmodulecall.api;

public enum CallType {
    VIDEO("video"),
    AUDIO("audio");


    private final String value;

    CallType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
