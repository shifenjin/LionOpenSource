package com.example.appmodulecall.impl.call_error;

public class CallException extends Exception {

    private String description;

    public CallException(String message, String description) {
        super("Call -> " + message);

        this.description = "异常 -> 呼叫 -> " + description;
    }

    public String getDescription() {
        return description;
    }

}
