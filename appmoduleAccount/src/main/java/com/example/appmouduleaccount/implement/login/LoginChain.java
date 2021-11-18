package com.example.appmouduleaccount.implement.login;

import androidx.annotation.NonNull;

import com.example.appmouduleaccount.api.login.ILoginCallBack;
import com.example.appmouduleaccount.api.login.domain.LoginRequest;
import com.example.appmouduleaccount.api.login.domain.LoginResult;

import java.util.List;

/**
 * 登录 责任链
 */
public class LoginChain {

    private List<LoginInterceptor> loginInterceptors;
    private LoginRequest loginRequest;
    private LoginResult loginResult;
    private final ILoginCallBack loginCallBack;
    private int index;


    public LoginChain(@NonNull List<LoginInterceptor> loginInterceptors, LoginRequest loginRequest, ILoginCallBack loginCallBack) {
        this.loginInterceptors = loginInterceptors;
        this.loginRequest = loginRequest;
        this.loginResult = new LoginResult();
        this.loginCallBack = loginCallBack;
        this.index = 0;
    }

    public LoginRequest request() {
        return loginRequest;
    }

    public LoginResult result() {
        return loginResult;
    }

    public void proceed() {

        if (index < 0) throw new IllegalArgumentException();

        // 登录责任链 完成
        if (index < loginInterceptors.size()) {
            LoginInterceptor loginInterceptor = loginInterceptors.get(index++);
            loginInterceptor.intercept(this);
        }
    }

    public ILoginCallBack getLoginCallBack() {
        return loginCallBack;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }
}
