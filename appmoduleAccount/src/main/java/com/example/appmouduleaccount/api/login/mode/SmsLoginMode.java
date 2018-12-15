package com.example.appmouduleaccount.api.login.mode;

import android.support.annotation.NonNull;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;

public class SmsLoginMode implements ILoginMode {
    private final String phoneNum;
    private final String verification;

    public SmsLoginMode(@NonNull String phoneNum, @NonNull String verification) {
        this.phoneNum = phoneNum;
        this.verification = verification;
    }

    @Override
    public void setModeData(LoginRequest loginRequest) {
        loginRequest.mobile = phoneNum;
        loginRequest.password = verification;
    }
}
