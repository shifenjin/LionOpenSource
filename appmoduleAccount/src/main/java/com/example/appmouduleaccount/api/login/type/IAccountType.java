package com.example.appmouduleaccount.api.login.type;


import android.support.annotation.NonNull;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;

public interface IAccountType {

    /**
     * 设置 账号类型
     */
    void setAccountType(@NonNull LoginRequest loginRequest);
}
