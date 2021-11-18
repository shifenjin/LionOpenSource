package com.example.appmodulecall.impl.call_webrtc;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appmodulecall.api.CallType;
import com.example.appmodulecall.impl.call_error.CallConnectionException;
import com.example.appmodulecall.impl.call_webrtc.module.LocalAudioProxy;
import com.example.appmodulecall.impl.call_webrtc.module.LocalVideoProxy;
import com.example.appmodulecall.impl.call_webrtc.module.WebRtcView;

import org.webrtc.MediaStream;
import org.webrtc.PeerConnectionFactory;

public class CallLocalMediaHandler {

    private static final String VIDEO_TRACK_ID = "ARDAMSv0";
    private static final String AUDIO_TRACK_ID = "ARDAMSa0";

    private WebRtcView mFullScreenRTCView;
    private WebRtcView mFloatedRTCView;

    private MediaStream localMediaStream;

    private LocalAudioProxy localAudioProxy;
    private LocalVideoProxy localVideoProxy;

    public CallLocalMediaHandler() {

    }

    /**
     * 创建 全屏视图控件
     */
    public WebRtcView createFullScreenRTCView(Context context) {
        mFullScreenRTCView = new WebRtcView(context);
        mFullScreenRTCView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
        mFullScreenRTCView.setVisibility(View.GONE);

        return mFullScreenRTCView;

    }

    /**
     * 创建 浮窗视图控件
     *
     */
    public WebRtcView createFloatedRTCView(Context context) {
        mFloatedRTCView = new WebRtcView(context);
        mFloatedRTCView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        mFloatedRTCView.setVisibility(View.GONE);

        return mFloatedRTCView;
    }


    /**
     * 设置 全屏视频控件媒体流
     *
     * @param mediaStream
     */
    public void setFullScreenStream(final MediaStream mediaStream) {
        if (mFullScreenRTCView.isAttachedToWindow()) {
            mFullScreenRTCView.post(new Runnable() {
                @Override
                public void run() {
                    mFullScreenRTCView.setStream(mediaStream);
                    mFullScreenRTCView.setVisibility(View.VISIBLE);

                    mFullScreenRTCView.invalidate();
                }
            });
        }
        else {
            mFullScreenRTCView.setStream(mediaStream);
            mFullScreenRTCView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置 浮窗视频控件媒体流
     *
     * @param mediaStream
     */
    public void setFloatedScreenStream(final MediaStream mediaStream) {
        if (mFloatedRTCView.isAttachedToWindow()) {
            mFloatedRTCView.post(new Runnable() {
                @Override
                public void run() {
                    mFloatedRTCView.setStream(mediaStream);
                    mFloatedRTCView.setVisibility(View.VISIBLE);
                    mFloatedRTCView.setZOrder(1);

                    mFullScreenRTCView.invalidate();
                }
            });
        }
        else {
            mFloatedRTCView.setStream(mediaStream);
            mFloatedRTCView.setVisibility(View.VISIBLE);
            mFloatedRTCView.setZOrder(1);
        }
    }

    public void initLocalMediaStream(Context context, PeerConnectionFactory peerConnectionFactory, String label, CallType callType) throws CallConnectionException {
        localMediaStream = peerConnectionFactory.createLocalMediaStream(label);

        if (localMediaStream == null) {
            throw new CallConnectionException(
                    "peerConnectionFactory.createLocalMediaStream : localMediaStream == null",
                    "创建本地媒体流失败");
        }

        localAudioProxy = new LocalAudioProxy();
        localAudioProxy.init(peerConnectionFactory, AUDIO_TRACK_ID);
        localMediaStream.addTrack(localAudioProxy.getAudioTrack());

        if (callType.equals(CallType.VIDEO)) {
            localVideoProxy = new LocalVideoProxy();
            localVideoProxy.init(context, peerConnectionFactory, VIDEO_TRACK_ID);
            localMediaStream.addTrack(localVideoProxy.getVideoTrack());
        }

    }
    public void setLocalAudioEnabled(boolean isEnable) {
        localAudioProxy.setAudioEnable(isEnable);
    }

    public MediaStream getLocalMediaStream() {
        return localMediaStream;
    }

    public void clear() {
        if (localAudioProxy != null) {
            localAudioProxy.clear();
            localAudioProxy = null;
        }

        if (localVideoProxy != null) {
            localVideoProxy.clear();
            localVideoProxy = null;
        }

        if (localMediaStream != null) {
            localMediaStream.dispose();
            localMediaStream = null;
        }
    }


    public boolean isLocalAudioEnabled() {
        return localAudioProxy.isLocalAudioEnabled();
    }

    public void setLocalVideoEnabled(boolean isEnable) {
        localVideoProxy.setVideoEnable(isEnable);
    }

    public boolean isLocalVideoEnabled() {
        return localVideoProxy.isLocalVideoEnabled();
    }

    public void setFrontCameraForRemoteVideo(boolean isFront) {
        // todo
    }

    public boolean isFrontCameraForRemoteVideo() {
        // todo
        return false;
    }

    public void setFloatViewLayout(final RelativeLayout.LayoutParams layoutParams) {
        if (mFloatedRTCView.isAttachedToWindow()) {
            mFloatedRTCView.post(new Runnable() {
                @Override
                public void run() {
                    mFloatedRTCView.setLayoutParams(layoutParams);
                }
            });
        }
        else {
            mFloatedRTCView.setLayoutParams(layoutParams);
        }

    }
}
