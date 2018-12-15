package com.example.appmouduleaccount.api.login;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;
import com.example.appmouduleaccount.api.login.mode.ILoginMode;
import com.example.appmouduleaccount.api.login.type.IAccountType;
import com.example.appmouduleaccount.implement.login.CheckLoginInterceptor;
import com.example.appmouduleaccount.implement.login.LoginChain;
import com.example.appmouduleaccount.implement.login.LoginInterceptor;
import com.example.appmouduleaccount.implement.login.NetRequestLoginInterceptor;

import java.util.ArrayList;
import java.util.List;

public final class AccountLogin {

    private LoginRequest loginRequest;

    private AccountLogin() {

    }

    public void login(ILoginCallBack loginCallBack) {

        List<LoginInterceptor> loginInterceptors = new ArrayList<>();
        // 校验
        loginInterceptors.add(new CheckLoginInterceptor());
        // 请求登录
        loginInterceptors.add(new NetRequestLoginInterceptor());
        // 缓存登录信息

        LoginChain loginChain = new LoginChain(loginInterceptors, loginRequest, loginCallBack);
        loginChain.proceed();
    }

    private void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    public static final class Builder {


        private LoginRequest loginRequest;

        public Builder() {
            this.loginRequest = new LoginRequest();
        }

        /**
         * 登录模式
         *
         * @param loginMode
         * @return
         */
        public Builder loginMode(ILoginMode loginMode) {

            loginMode.setModeData(loginRequest);

            return this;
        }

        /**
         * 账号类型
         */
        public Builder accountType(IAccountType accountType) {

            accountType.setAccountType(loginRequest);

            return this;
        }

        public AccountLogin build() {

            AccountLogin accountLogin = new AccountLogin();
            accountLogin.setLoginRequest(loginRequest);
            return accountLogin;
        }
    }

}
