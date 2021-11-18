package com.example.appmouduleaccount.api.login.mode;

import androidx.annotation.NonNull;

import com.example.appmouduleaccount.implement.login.mode.SmsLoginMode;

public class SmsLoginModeFactory {

    public static ILoginMode create(@NonNull String phoneNum, @NonNull String verification) {
        return new SmsLoginMode(phoneNum, verification);
    }

}
