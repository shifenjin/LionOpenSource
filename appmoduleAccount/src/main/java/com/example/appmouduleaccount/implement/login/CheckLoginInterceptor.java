package com.example.appmouduleaccount.implement.login;

import android.util.Log;

import com.example.appmouduleaccount.api.login.domain.LoginResult;

/**
 * 校验登录 责任者
 */
public class CheckLoginInterceptor implements LoginInterceptor {
    @Override
    public void intercept(LoginChain chain) {

        boolean isLicense = true;

        Log.i("haha", "执行校验登录");
//        LoginRequest loginRequest = chain.request();

        // 校验成功
        if (isLicense) {
            chain.proceed();
        }
        // 校验失败
        else {
            LoginResult loginResult = chain.result();
            loginResult.isLoginSuccess = false;
            loginResult.resultMsg = "校验失败";

            chain.getLoginCallBack().onError(loginResult);
        }

    }
}
