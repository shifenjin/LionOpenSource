package com.example.appmodulecall.api;

import android.view.ViewGroup;

public interface ICallEffectHandler {

    /**
     * 设置 本地音频是否开启
     *
     * @param isEnable
     */
    void setLocalAudioEnabled(boolean isEnable);

    /**
     * 获取 本地音频是否开启
     *
     * @return
     */
    boolean getLocalAudioEnabled();



    /**
     * 设置 远程视频是否开启
     *
     * @param isEnable
     */
    void setLocalVideoForRemoteEnabled(boolean isEnable);

    /**
     * 获取 远程视频是否开启
     *
     * @return
     */
    boolean getLocalVideoForRemoteEnabled();



    /**
     * 设置 前置摄像头负责远程视频
     *
     * @param isFront   false 则设置后置摄像头负责远程视频
     */
    void setFrontCameraForRemoteVideo(boolean isFront);

    /**
     * 获取 是否为前置摄像头负责远程视频
     *
     * @return
     */
    boolean isFrontCameraForRemoteVideo();

    /**
     * 设置 浮窗视频控件大小位置
     *
     */
    void setFloatViewLayout(int x, int y, int width, int height);

}
