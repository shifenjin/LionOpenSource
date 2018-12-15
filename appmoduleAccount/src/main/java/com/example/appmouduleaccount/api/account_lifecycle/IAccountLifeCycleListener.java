package com.example.appmouduleaccount.api.account_lifecycle;

/**
 * 账户生命周期回调监听
 */
public interface IAccountLifeCycleListener {

    // 登录 - 获取

    // 登录 - session初始化前
    void onBeforeAccountInit();

    // 登录 - session初始化后
    void onAfterAccountInit();

    // 退出登录 - session清除前
    void onBeforeAccountClear();

    // 退出登录 - session清除后
    void onAfterAccountClear();
}
