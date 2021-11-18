package com.example.appmodulecall.impl.call_error;

public class CallConnectionException extends CallException{

    public CallConnectionException(String message, String description) {
        super("CallConnection -> " + message, "webRTC连接 -> " + description);
    }
}
