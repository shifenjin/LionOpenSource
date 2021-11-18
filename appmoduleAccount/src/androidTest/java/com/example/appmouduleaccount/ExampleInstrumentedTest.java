package com.example.appmouduleaccount;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.appmouduleaccount.api.IAccountService;
import com.example.appmouduleaccount.api.login.IAccountLoginBuilder;
import com.example.appmouduleaccount.api.login.ILoginCallBack;
import com.example.appmouduleaccount.api.login.domain.LoginResult;
import com.example.appmouduleaccount.api.login.mode.AccountLoginModeFactory;
import com.example.appmouduleaccount.api.login.type.AccountType;
import com.example.appmouduleaccount.implement.AccountServiceImpl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    static IAccountService accountService;

    @BeforeClass
    public static void init() {
        accountService = new AccountServiceImpl();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.appmouduleaccount.test", appContext.getPackageName());

        boolean isLogin = accountService.isLogin();

        try {
            Thread.sleep(3333);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(isLogin, false);
    }

    @Test
    public void testLogin() {

        IAccountLoginBuilder loginBuilder = accountService.createLoginBuilder();
        loginBuilder.loginMode(AccountLoginModeFactory.create("user", "psw"))
                .accountType(AccountType.STAFF)
                .build()
                .login(new ILoginCallBack() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        assertEquals(loginResult.isNetRequsetLoginSuccess, true);
                    }

                    @Override
                    public void onError(LoginResult loginResult) {
                        assertEquals(loginResult.isNetRequsetLoginSuccess, false);
                    }

                    @Override
                    public void onProgress() {
                    }
                });

    }
}
