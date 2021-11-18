package com.example.appmouduleaccount.implement.init;

import com.example.appmouduleaccount.implement.AccountManager;

public class AccountInit {

    public void init() {

        AccountManager.getInstance().notifyBeforeAccountInit();
        // 初始化session
        AccountManager.getInstance().notifyAfterAccountInit();


    }
}
