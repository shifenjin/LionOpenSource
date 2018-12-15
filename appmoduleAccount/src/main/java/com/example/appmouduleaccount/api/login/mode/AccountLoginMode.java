package com.example.appmouduleaccount.api.login.mode;

import android.support.annotation.NonNull;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;

public class AccountLoginMode implements ILoginMode {

    private final String userName;
    private final String password;

    public AccountLoginMode(@NonNull String userName, @NonNull String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void setModeData(LoginRequest loginRequest) {
        loginRequest.userId = userName;
        loginRequest.password = password;
    }
}
