package com.example.appmouduleaccount.implement.login;

import com.example.appmouduleaccount.api.login.IAccountLogin;
import com.example.appmouduleaccount.api.login.ILoginCallBack;
import com.example.appmouduleaccount.api.login.domain.LoginRequest;

import java.util.ArrayList;
import java.util.List;

public final class AccountLogin implements IAccountLogin {

    private LoginRequest loginRequest;

    public AccountLogin() {

    }

    @Override
    public void login(ILoginCallBack loginCallBack) {

        List<LoginInterceptor> loginInterceptors = new ArrayList<>();
        // 校验
        loginInterceptors.add(new CheckLoginInterceptor());
        // 请求登录
        loginInterceptors.add(new NetRequestLoginInterceptor());
        // 缓存登录信息
        loginInterceptors.add(new StoreLoginInterceptor());

        LoginChain loginChain = new LoginChain(loginInterceptors, loginRequest, loginCallBack);
        loginChain.proceed();
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

}
