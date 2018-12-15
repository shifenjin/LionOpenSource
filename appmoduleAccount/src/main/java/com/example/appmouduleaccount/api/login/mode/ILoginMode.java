package com.example.appmouduleaccount.api.login.mode;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;

public interface ILoginMode {

    /**
     * 设置 登录数据
     */
    void setModeData(LoginRequest loginRequest);
}
