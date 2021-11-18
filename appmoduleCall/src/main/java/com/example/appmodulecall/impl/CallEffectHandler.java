package com.example.appmodulecall.impl;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.appmodulecall.api.ICallEffectHandler;
import com.example.appmodulecall.impl.call_webrtc.CallLocalMediaHandler;

public class CallEffectHandler implements ICallEffectHandler {

    private final CallLocalMediaHandler callLocalMediaHandler;

    public CallEffectHandler(CallLocalMediaHandler callLocalMediaHandler) {
        this.callLocalMediaHandler = callLocalMediaHandler;
    }

    /**
     * 设置 本地音频是否开启
     *
     * @param isEnable
     */
    @Override
    public void setLocalAudioEnabled(boolean isEnable) {
        callLocalMediaHandler.setLocalAudioEnabled(isEnable);
    }

    /**
     * 获取 本地音频是否开启
     *
     * @return
     */
    @Override
    public boolean getLocalAudioEnabled() {
        return callLocalMediaHandler.isLocalAudioEnabled();
    }

    /**
     * 设置 本地对远程视频是否开启
     *
     * @param isEnable
     */
    @Override
    public void setLocalVideoForRemoteEnabled(boolean isEnable) {
        callLocalMediaHandler.setLocalVideoEnabled(isEnable);
    }

    /**
     * 获取 本地对远程视频是否开启
     *
     * @return
     */
    @Override
    public boolean getLocalVideoForRemoteEnabled() {
        return callLocalMediaHandler.isLocalVideoEnabled();
    }

    /**
     * 设置 前置摄像头负责远程视频
     *
     * @param isFront false 则设置后置摄像头负责远程视频
     */
    @Override
    public void setFrontCameraForRemoteVideo(boolean isFront) {
        callLocalMediaHandler.setFrontCameraForRemoteVideo(isFront);
    }

    /**
     * 获取 是否为前置摄像头负责远程视频
     *
     * @return
     */
    @Override
    public boolean isFrontCameraForRemoteVideo() {
        return callLocalMediaHandler.isFrontCameraForRemoteVideo();
    }

    /**
     * 设置 浮窗视频控件位置
     *
     */
    @Override
    public void setFloatViewLayout(int x, int y, int width, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.leftMargin = x;
        params.topMargin = y;
        callLocalMediaHandler.setFloatViewLayout(params);
    }
}
