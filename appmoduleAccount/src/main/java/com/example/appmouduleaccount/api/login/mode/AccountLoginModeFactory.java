package com.example.appmouduleaccount.api.login.mode;

import androidx.annotation.NonNull;

import com.example.appmouduleaccount.implement.login.mode.AccountLoginMode;

public class AccountLoginModeFactory {
    public static ILoginMode create(@NonNull String userName, @NonNull String password) {
        return new AccountLoginMode(userName, password);
    }
}
