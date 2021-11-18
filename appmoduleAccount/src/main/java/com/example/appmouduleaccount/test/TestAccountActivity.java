package com.example.appmouduleaccount.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.appmouduleaccount.api.login.IAccountLoginBuilder;
import com.example.appmouduleaccount.api.login.mode.AccountLoginModeFactory;
import com.example.appmouduleaccount.api.login.type.AccountType;
import com.example.appmouduleaccount.implement.AccountServiceImpl;
import com.example.appmouduleaccount.R;
import com.example.appmouduleaccount.api.login.ILoginCallBack;
import com.example.appmouduleaccount.api.login.domain.LoginResult;

public class TestAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_account);

        AccountServiceImpl accountManager = new AccountServiceImpl();
        IAccountLoginBuilder loginBuilder = accountManager.createLoginBuilder();
        loginBuilder
                .loginMode(AccountLoginModeFactory.create("userName", "password"))
                .accountType(AccountType.STAFF)
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
