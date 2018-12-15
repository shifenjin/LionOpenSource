package com.example.appmouduleaccount.api.login;

import com.example.appmouduleaccount.api.login.domain.LoginResult;

/**
 * 登录生命周期回调
 */
public interface ILoginCallBack {

    void onSuccess(LoginResult loginResult);

    void onError(LoginResult loginResult);

    void onProgress();
}
