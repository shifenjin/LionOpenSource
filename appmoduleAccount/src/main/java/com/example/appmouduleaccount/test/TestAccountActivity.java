package com.example.appmouduleaccount.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.appmouduleaccount.implement.AccountServiceImpl;
import com.example.appmouduleaccount.R;
import com.example.appmouduleaccount.api.login.AccountLogin;
import com.example.appmouduleaccount.api.login.ILoginCallBack;
import com.example.appmouduleaccount.api.login.domain.LoginResult;
import com.example.appmouduleaccount.api.login.mode.AccountLoginMode;
import com.example.appmouduleaccount.api.login.type.StaffAccountType;

public class TestAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_account);

        AccountServiceImpl accountManager = new AccountServiceImpl();
        AccountLogin.Builder loginBuilder = accountManager.createLoginBuilder();
        loginBuilder
                .loginMode(new AccountLoginMode("userName", "password"))
                .accountType(new StaffAccountType())
                .build()
                .login(new ILoginCallBack() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.i("haha", "登录成功 回调");
                    }

                    @Override
                    public void onError(LoginResult loginResult) {
                        Log.i("haha", "登录失败 回调 -> 原因 : " + loginResult.resultMsg);
                    }

                    @Override
                    public void onProgress() {
                        Log.i("haha", "登录中 回调");
                    }
                });

    }
}
