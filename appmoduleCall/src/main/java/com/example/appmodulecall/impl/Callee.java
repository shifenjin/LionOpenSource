package com.example.appmodulecall.impl;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

import com.example.appmodulecall.api.CallType;
import com.example.appmodulecall.api.ICallEffectHandler;
import com.example.appmodulecall.api.ICallLifeCycleListener;
import com.example.appmodulecall.api.ICallee;
import com.example.appmodulecall.impl.call_error.CallConnectionException;
import com.example.appmodulecall.impl.call_error.CallExceptionHandler;
import com.example.appmodulecall.impl.call_msg.CallMsg;
import com.example.appmodulecall.impl.call_webrtc.module.WebRtcView;

import java.util.Map;

public class Callee extends Call implements ICallee {
    protected Callee(String callId, String callerUserId, CallType callType) {
        super(callId, callType);

        this.callerUserId = callerUserId;
        this.calleeUserId = CallManager.getInstance().getUserId();
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
    public void createCallView(Activity activity) {
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

    public void onAddCandidate(CallMsg callMsg) {
        Map<String, String> callMsgData = callMsg.getCallMsgData();

        callConnectionHandler.addIceCandidate(callMsgData);
    }

    @Override
    public void answerCall(Context context) {
        initCallConnection(context);

        // 接收 peerConnection 请求
        callConnectionHandler.createAnswer(callType);
    }
}
