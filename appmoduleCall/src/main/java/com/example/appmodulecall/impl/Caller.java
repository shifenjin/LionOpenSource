package com.example.appmodulecall.impl;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.example.appmodulecall.api.CallType;
import com.example.appmodulecall.api.ICallEffectHandler;
import com.example.appmodulecall.api.ICaller;
import com.example.appmodulecall.api.ICallLifeCycleListener;
import com.example.appmodulecall.impl.call_msg.CallMsg;
import com.example.appmodulecall.impl.call_msg.CallMsgKey;
import com.example.appmodulecall.impl.call_webrtc.module.WebRtcView;

import java.util.Map;

public class Caller extends Call implements ICaller {

    public Caller(String callId, String calleeUserId, CallType callType) {
        super(callId, callType);

        this.calleeUserId = calleeUserId;
        this.callerUserId = CallManager.getInstance().getUserId();
    }

    @Override
    public void addCallLifeCycleListener(ICallLifeCycleListener callLifeCycleListener) {
        super.addCallLifeCycleListener(callLifeCycleListener);
    }

    @Override
    public void removeCallLifeCycleListener(ICallLifeCycleListener callLifeCycleListener) {
        super.removeCallLifeCycleListener(callLifeCycleListener);
    }

    @Override
    public void createCallView(final Activity activity) {
        super.createCallView(activity);

    }

    @Override
    public void hangup() {
        super.hangup();
    }

    @Override
    public ICallEffectHandler getCallEffectHandler() {
        return super.getCallEffectHandler();
    }

    @Override
    public void startCall(Context context) {

        initCallConnection(context);

        // 发起 peerConnection 请求
        callConnectionHandler.createOffer(callType);
    }

    public void onCallAnswer(CallMsg callMsg) {
        Map<String, String> callMsgData = callMsg.getCallMsgData();
        String type = callMsgData.get(CallMsgKey.KEY_SDP_TYPE);
        String sdp = callMsgData.get(CallMsgKey.KEY_SDP);

        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(sdp) && type.equals("answer")) {
            callConnectionHandler.setRemoteSdp(sdp);
        }

    }
}
