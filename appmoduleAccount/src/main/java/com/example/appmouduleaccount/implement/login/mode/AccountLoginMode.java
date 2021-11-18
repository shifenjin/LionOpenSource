package com.example.appmouduleaccount.implement.login.mode;

import androidx.annotation.NonNull;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;
import com.example.appmouduleaccount.api.login.mode.ILoginMode;

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
