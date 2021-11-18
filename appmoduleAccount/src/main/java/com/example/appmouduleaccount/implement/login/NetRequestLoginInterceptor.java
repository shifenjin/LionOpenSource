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


        Log.i("haha", "执行网络请求");
        LoginRequest loginRequest = chain.request();

        // 网络请求成功
        if (isRequestSuccess) {

            LoginResult loginResult = chain.getLoginResult();
            loginResult.isNetRequsetLoginSuccess = true;
            loginResult.resultMsg = "网络请求成功";

            chain.proceed();
        }
        // 网络请求失败
        else {
            LoginResult loginResult = chain.result();
            loginResult.isNetRequsetLoginSuccess = false;
            loginResult.resultMsg = "网络请求失败";

            chain.getLoginCallBack().onError(loginResult);
        }
    }
}
