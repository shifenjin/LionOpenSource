package com.example.appmouduleaccount.api.login;

import com.example.appmouduleaccount.api.login.mode.ILoginMode;
import com.example.appmouduleaccount.api.login.type.AccountType;

public interface IAccountLoginBuilder {

    /**
     * 设置 登录模式
     *
     * @param loginMode
     * @return
     */
    IAccountLoginBuilder loginMode(ILoginMode loginMode);

    /**
     * 设置 账号类型
     *
     * @param accountType
     * @return
     */
    IAccountLoginBuilder accountType(AccountType accountType);

    /**
     * 创建 账号登录对象
     *
     * @return
     */
    IAccountLogin build();

}
