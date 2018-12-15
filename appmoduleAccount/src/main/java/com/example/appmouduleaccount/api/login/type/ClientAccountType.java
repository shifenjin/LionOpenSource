package com.example.appmouduleaccount.api.login.type;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;

public class ClientAccountType implements IAccountType {

    /**
     * 客户
     */
    private final String CLIENT = "CLIENT";

    @Override
    public void setAccountType(LoginRequest loginRequest) {
        loginRequest.account_type = CLIENT;
    }
}
