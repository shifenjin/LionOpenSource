package com.example.appmouduleaccount.api;

import com.example.appmouduleaccount.api.account_lifecycle.IAccountLifeCycleListener;
import com.example.appmouduleaccount.api.init.IInitAccountCallBack;
import com.example.appmouduleaccount.api.login.IAccountLoginBuilder;
import com.example.appmouduleaccount.implement.login.AccountLoginBuilder;

public interface IAccountService {

    /**
     * 创建登录对象
     *
     * @return
     */
    IAccountLoginBuilder createLoginBuilder();

    /**
     * 是否已登录
     *
     * @return
     */
    boolean isLogin();

    /**
     * 初始化账户
     *
     * @param initAccountCallBack
     */
    void initAccount(IInitAccountCallBack initAccountCallBack);

    /**
     * 增加 账户生命周期监听
     *
     * @param accountLifeCycleListener
     */
    void addAccountLifeCycleListener(IAccountLifeCycleListener accountLifeCycleListener);

    /**
     * 移除 账户生命周期监听
     *
     * @param accountLifeCycleListener
     */
    void removeAccountLifeCycleListener(IAccountLifeCycleListener accountLifeCycleListener);

//    void logout(ILogoutCallBack logoutCallBack);
}
