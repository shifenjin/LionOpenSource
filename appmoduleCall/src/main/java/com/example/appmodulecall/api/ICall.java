package com.example.appmodulecall.api;

import android.app.Activity;

public interface ICall {
    /**
     * 添加 呼叫 生命周期监听
     *
     * @param callLifeCycleListener
     */
    void addCallLifeCycleListener(ICallLifeCycleListener callLifeCycleListener);

    /**
     * 移除 呼叫 生命周期监听
     *
     * @param callLifeCycleListener
     */
    void removeCallLifeCycleListener(ICallLifeCycleListener callLifeCycleListener);

    /**
     * 创建 呼叫 视图控件（视频）
     */
    void createCallView(Activity activity);

    /**
     * 挂断
     */
    void hangup();

    /**
     * 获取 呼叫效果处理器
     *
     * @return
     */
    ICallEffectHandler getCallEffectHandler();
}
