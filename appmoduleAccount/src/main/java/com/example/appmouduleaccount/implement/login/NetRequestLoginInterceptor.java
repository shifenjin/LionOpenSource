package com.example.appmouduleaccount.implement.login;

import android.util.Log;

import com.example.appmouduleaccount.api.login.domain.LoginRequest;
import com.example.appmouduleaccount.api.login.domain.LoginResult;

/**
 * 网络请求 责任者
 */
public class NetRequestLoginInterceptor implements LoginInterceptor {

    @Override
    public void intercept(LoginChain chain) {
        boolean isRequestSuccess = true;

        chain.getLoginCallBack().onProgress();

        Log.i("haha", "执行网络请求");
        LoginRequest loginRequest = chain.request();

        // 网络请求成功
        if (isRequestSuccess) {
            chain.proceed();
        }
        // 网络请求失败
        else {
            LoginResult loginResult = chain.result();
            loginResult.isLoginSuccess = false;
            loginResult.resultMsg = "网络请求失败";

            chain.getLoginCallBack().onError(loginResult);
        }
    }
}
