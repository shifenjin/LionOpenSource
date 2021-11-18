package com.example.appmodulecall.impl;

import android.app.Activity;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appmodulecall.api.CallType;
import com.example.appmodulecall.api.ICallEffectHandler;
import com.example.appmodulecall.api.ICallLifeCycleListener;
import com.example.appmodulecall.impl.call_error.CallConnectionException;
import com.example.appmodulecall.impl.call_error.CallExceptionHandler;
import com.example.appmodulecall.impl.call_msg.CallMsg;
import com.example.appmodulecall.impl.call_msg.CallMsgKey;
import com.example.appmodulecall.impl.call_msg.CallMsgManager;
import com.example.appmodulecall.impl.call_msg.CallMsgType;
import com.example.appmodulecall.impl.call_webrtc.CallConnectionHandler;
import com.example.appmodulecall.impl.call_webrtc.CallLocalMediaHandler;
import com.example.appmodulecall.impl.call_webrtc.module.WebRtcView;

import org.webrtc.PeerConnectionFactory;

import java.util.HashMap;

public abstract class Call {

    protected String callId;
    protected CallType callType;

    protected String callerUserId;
    protected String calleeUserId;

    protected CallLocalMediaHandler callLocalMediaHandler;
    protected CallConnectionHandler callConnectionHandler;
    protected CallLifeCycleHandler callLifeCycleHandler;
    protected ICallEffectHandler callEffectHandler;


    protected RelativeLayout mCallView;

    protected Call(String callId, CallType callType) {
        this.callId = callId;
        this.callType = callType;

        callLifeCycleHandler = new CallLifeCycleHandler();
        callLocalMediaHandler = new CallLocalMediaHandler();
        callConnectionHandler = new CallConnectionHandler(this, callType, callLocalMediaHandler);
        callEffectHandler = new CallEffectHandler(callLocalMediaHandler);
    }

    protected void addCallLifeCycleListener(ICallLifeCycleListener callLifeCycleListener) {
        callLifeCycleHandler.addCallLifeCycleListener(callLifeCycleListener);
    }

    protected void removeCallLifeCycleListener(ICallLifeCycleListener callLifeCycleListener) {
        callLifeCycleHandler.removeCallLifeCycleListener(callLifeCycleListener);

    }

    protected void createCallView(final Activity activity) {
        if (CallType.VIDEO.equals(callType)) {
            mCallView = new RelativeLayout(activity);
            mCallView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));
            mCallView.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.black));
            mCallView.setVisibility(View.GONE);

            callLifeCycleHandler.notifyCallViewCreated(mCallView);
        }
    }

    protected void initCallConnection(Context context) {
        Context applicationContext = context.getApplicationContext();

        try {
            // 初始化 PeerConnectionFactory
            PeerConnectionFactory peerConnectionFactory = callConnectionHandler.initPeerConnectionFactory();
            // 初始化  媒体流
            callLocalMediaHandler.initLocalMediaStream(applicationContext, peerConnectionFactory, "ARDAMS", callType);

            if (CallType.VIDEO.equals(callType)) {
                // 创建 本地视频视图控件
                final WebRtcView fullScreenRTCView = callLocalMediaHandler.createFullScreenRTCView(applicationContext);
                callLocalMediaHandler.setFullScreenStream(callLocalMediaHandler.getLocalMediaStream());
                final WebRtcView floatedRTCView = callLocalMediaHandler.createFloatedRTCView(applicationContext);

                if (mCallView.isAttachedToWindow()) {
                    mCallView.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallView.addView(fullScreenRTCView,
                                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                            mCallView.addView(floatedRTCView,
                                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                        }
                    });
                }
                else {
                    mCallView.addView(fullScreenRTCView,
                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

                    final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                    int width = displayMetrics.widthPixels / 4;
                    int height = displayMetrics.heightPixels / 4;
                    mCallView.addView(floatedRTCView,
                            new RelativeLayout.LayoutParams(width, height));
                }
            }
            // 创建 peerConnection
            callConnectionHandler.initPeerConnection();
            // 设置 本地媒体流
            callConnectionHandler.addLocalStream();
        } catch (CallConnectionException e) {
            e.printStackTrace();

            onEndForLocalException(e);
        }
    }

    protected void hangup() {
        onHangup();

        CallMsg callMsg = new CallMsg(CallMsgType.SEND_HANG_UP, new HashMap<String, String>());
        CallMsgManager.sendCallMsg(this, callMsg);
    }

    public void onHangup() {
        callLifeCycleHandler.notifyCallOnHangUp();

        CallManager.getInstance().clearCall();
    }

    public void onEndForLocalException(CallConnectionException e) {

        CallExceptionHandler.logError(e);

        HashMap<Object, Object> data =
                new HashMap<>();
        data.put(CallMsgKey.KEY_END_EXCEPTION_DESCRIPTION, e.getDescription());
        CallMsg callMsg = new CallMsg(CallMsgType.SEND_EXCEPTION_END, data);
        CallMsgManager.sendCallMsg(this, callMsg);

        callLifeCycleHandler.notifyCallException(e.getDescription());

        CallManager.getInstance().clearCall();
    }

    public void onEndForRemoteException(CallMsg callMsg) {

        String description = callMsg.getCallMsgData().get(CallMsgKey.KEY_END_EXCEPTION_DESCRIPTION);
        callLifeCycleHandler.notifyCallException(description);

        CallManager.getInstance().clearCall();
    }

    public void clearCall() {
        callConnectionHandler.clear();

        if (mCallView != null) {
            if (mCallView.isAttachedToWindow()) {
                mCallView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallView != null) {
                            mCallView.removeAllViews();
                            mCallView = null;
                        }
                    }
                });
            }
            else {
                mCallView.removeAllViews();
                mCallView = null;
            }
        }
    }

    public String getCallId() {
        return callId;
    }

    public CallType getCallType() {
        return callType;
    }

    public String getCallerUserId() {
        return callerUserId;
    }

    public String getCalleeUserId() {
        return calleeUserId;
    }

    public CallLifeCycleHandler getCallLifeCycleHandler() {
        return callLifeCycleHandler;
    }

    public ICallEffectHandler getCallEffectHandler() {
        return callEffectHandler;
    }
}
