package com.example.appmouduleaccount.implement.login;

import android.util.Log;

import com.example.appmouduleaccount.api.login.domain.LoginResult;

/**
 * 登录 - 储存 责任者
 */
class StoreLoginInterceptor implements LoginInterceptor {
    @Override
    public void intercept(LoginChain chain) {

        boolean isStoreSuccess = true;

        Log.i("haha", "执行 缓存登录信息");

        // TODO: 2019/2/19 缓存登录信息

        LoginResult loginResult = chain.result();
        // 缓存成功
        if (isStoreSuccess) {
            loginResult.isStoreLoginSuccess = false;

            chain.proceed();
        }
        // 校验失败
        else {
            loginResult.isStoreLoginSuccess = false;

        }

        chain.getLoginCallBack().onSuccess(loginResult);
    }
}
