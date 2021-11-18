package com.example.appmodulecall.api;

public interface ICallCreateCallBack {

    void onSuccess(ICaller caller);

    void onFailed(String reason);
}
