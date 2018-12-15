package com.example.appmouduleaccount.implement;

import com.example.appmouduleaccount.api.account_lifecycle.IAccountLifeCycleListener;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    private static AccountManager mInstance;

    private AccountManager(){
        accountLifeCycleListeners = new ArrayList<>();
    }

    public static AccountManager getInstance() {
        if (mInstance == null) {
            synchronized (AccountManager.class) {
                if (mInstance == null) {
                    mInstance = new AccountManager();
                }
            }
        }

        return mInstance;
    }

    public List<IAccountLifeCycleListener> getAccountLifeCycleListeners() {
        return accountLifeCycleListeners;
    }

    private List<IAccountLifeCycleListener> accountLifeCycleListeners;

    public boolean isLogin() {
        return false;
    }

    public synchronized void addAccountLifeCycleListener(IAccountLifeCycleListener accountLifeCycleListener) {
        accountLifeCycleListeners.add(accountLifeCycleListener);
    }

    public synchronized void removeAccountLifeCycleListener(IAccountLifeCycleListener accountLifeCycleListener) {
        accountLifeCycleListeners.remove(accountLifeCycleListener);
    }

    public void onBeforeAccountInit() {
        for (IAccountLifeCycleListener accountLifeCycleListener : accountLifeCycleListeners) {
            accountLifeCycleListener.onBeforeAccountInit();
        }
    }
    public void onAfterAccountInit() {
        for (IAccountLifeCycleListener accountLifeCycleListener : accountLifeCycleListeners) {
            accountLifeCycleListener.onAfterAccountInit();
        }
    }
}
