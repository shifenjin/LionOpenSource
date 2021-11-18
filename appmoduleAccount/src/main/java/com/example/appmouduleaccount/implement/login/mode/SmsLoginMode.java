package com.example.appmouduleaccount.implement.login.mode;

import androidx.annotation.NonNull;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;
import com.example.appmouduleaccount.api.login.mode.ILoginMode;

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
