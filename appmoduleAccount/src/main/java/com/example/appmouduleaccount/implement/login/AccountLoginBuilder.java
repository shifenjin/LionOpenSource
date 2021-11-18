package com.example.appmouduleaccount.implement.login;

import com.example.appmouduleaccount.api.login.IAccountLogin;
import com.example.appmouduleaccount.api.login.IAccountLoginBuilder;
import com.example.appmouduleaccount.api.login.domain.LoginRequest;
import com.example.appmouduleaccount.api.login.mode.ILoginMode;
import com.example.appmouduleaccount.api.login.type.AccountType;

public class AccountLoginBuilder implements IAccountLoginBuilder {

    private LoginRequest loginRequest;

    public AccountLoginBuilder() {
        this.loginRequest = new LoginRequest();
    }

    /**
     * 登录模式
     *
     * @param loginMode
     * @return
     */
    public IAccountLoginBuilder loginMode(ILoginMode loginMode) {

        loginMode.setModeData(loginRequest);

        return this;
    }

    /**
     * 账号类型
     */
    public IAccountLoginBuilder accountType(AccountType accountType) {

        loginRequest.account_type = accountType.getValue();

        return this;
    }

    public IAccountLogin build() {

        AccountLogin accountLogin = new AccountLogin();
        accountLogin.setLoginRequest(loginRequest);
        return accountLogin;
    }

}
