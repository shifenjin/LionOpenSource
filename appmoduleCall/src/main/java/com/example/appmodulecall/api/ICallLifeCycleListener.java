package com.example.appmodulecall.api;

import android.widget.RelativeLayout;

import com.example.appmodulecall.impl.call_error.CallException;

public interface ICallLifeCycleListener {

    /**
     * 视频 视图控件 已创建
     *
     * @param callView
     */
    void onCallViewCreated(RelativeLayout callView);

    /**
     * 连接中
     */
    void onCallConnecting();


    /**
     * 已连接
     */
    void onCallConnected();

    /**
     * 挂断
     */
    void onHangUp();

    /**
     * 异常结束
     *
     * @param reason    异常描述
     */
    void onEndForException(String reason);
}
