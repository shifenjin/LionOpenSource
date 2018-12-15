package com.example.appmouduleaccount.api.login.type;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;

public class StaffAccountType implements IAccountType {

    /**
     * 员工
     */
    private final String STAFF = "STAFF";

    @Override
    public void setAccountType(LoginRequest loginRequest) {
        loginRequest.account_type = STAFF;
    }
}
