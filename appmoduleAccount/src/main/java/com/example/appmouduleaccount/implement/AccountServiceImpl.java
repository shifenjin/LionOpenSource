package com.example.appmouduleaccount.implement;

import com.example.appmouduleaccount.api.account_lifecycle.IAccountLifeCycleListener;
import com.example.appmouduleaccount.api.init.IInitAccountCallBack;
import com.example.appmouduleaccount.implement.init.AccountInit;
import com.example.appmouduleaccount.api.login.AccountLogin;
import com.example.appmouduleaccount.api.IAccountService;

public class AccountServiceImpl implements IAccountService {



    @Override
    public AccountLogin.Builder createLoginBuilder() {
        return new AccountLogin.Builder();
    }

    @Override
    public boolean isLogin() {
        return AccountManager.getInstance().isLogin();
    }

    @Override
    public void initAccount(IInitAccountCallBack initAccountCallBack) {
        AccountInit accountInit = new AccountInit();
        accountInit.init();
    }

    @Override
    public void addAccountLifeCycleListener(IAccountLifeCycleListener accountLifeCycleListener) {
        AccountManager.getInstance().addAccountLifeCycleListener(accountLifeCycleListener);
    }

    @Override
    public void removeAccountLifeCycleListener(IAccountLifeCycleListener accountLifeCycleListener) {
        AccountManager.getInstance().removeAccountLifeCycleListener(accountLifeCycleListener);
    }


}
