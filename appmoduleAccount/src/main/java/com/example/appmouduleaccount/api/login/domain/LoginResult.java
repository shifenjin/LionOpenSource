package com.example.appmouduleaccount.api.login.domain;

public class LoginResult {

    // 校验账号 是否成功
    public boolean isCheckLoginSuccess;

    // 网络请求登录 是否成功
    public boolean isNetRequsetLoginSuccess;

    // 缓存登录信息 是否成功
    public boolean isStoreLoginSuccess;

    // 登录结果信息
    public String resultMsg;
}
