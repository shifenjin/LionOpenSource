package com.example.appmouduleaccount.api.login;

public interface IAccountLogin {

    /**
     * 登录
     *
     * @param loginCallBack     登录回调
     */
    void login(ILoginCallBack loginCallBack);
}
