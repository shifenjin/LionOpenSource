package com.example.appmodulecall.impl;

import android.util.Log;

import com.example.appmodulecall.api.CallType;
import com.example.appmodulecall.api.ICallCreateCallBack;
import com.example.appmodulecall.api.ICallIncomeListener;
import com.example.appmodulecall.impl.call_msg.CallMsg;
import com.example.appmodulecall.impl.call_msg.CallMsgKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CallManager {

    private CallManager() { }

    private static CallManager mInstance;

    public static CallManager getInstance() {
        if (mInstance == null) {
            synchronized (CallManager.class) {
                if (mInstance == null)
                    mInstance = new CallManager();
            }
        }

        return mInstance;
    }

    private List<ICallIncomeListener> callIncomeListeners = new ArrayList<>();

    public void addCallIncomeListener(ICallIncomeListener callManagerListener) {
        callIncomeListeners.add(callManagerListener);
    }

    public void removeCallIncomeListener(ICallIncomeListener callManagerListener) {
        callIncomeListeners.remove(callManagerListener);
    }

    public void onCallIncome(CallMsg callMsg) {
        // 已经有呼叫
        if (hasCall()) {
            for (ICallIncomeListener callIncomeListener : callIncomeListeners) {
                callIncomeListener.onCallIncomingFailed("已经有呼叫");
            }
            return;
        }

        Map<String, String> callMsgData = callMsg.getCallMsgData();

        String callId = callMsgData.get(CallMsgKey.KEY_CALL_ID);
        String callerUserId = callMsgData.get(CallMsgKey.KEY_CALLER_USER_ID);
        CallType callType = null;
        if (callMsgData.get(CallMsgKey.KEY_CALL_TYPE).equals("video"))
            callType = CallType.VIDEO;
        else if (callMsgData.get(CallMsgKey.KEY_CALL_TYPE).equals("audio"))
            callType = CallType.AUDIO;

        createCallee(callId, callerUserId, callType);
    }

    private Caller caller;
    private Callee callee;

    public boolean hasCall() {
        return caller != null || callee != null;
    }

    private boolean hasCaller() {
        return caller != null;
    }

    private boolean hasCallee() {
        return callee != null;
    }

    public synchronized void createCaller(String calleeUserId, CallType callType, ICallCreateCallBack callCreateCallBack) {

        if (hasCall()) {
            Log.w(this.getClass().getSimpleName(), "createCaller failed : has call");
            callCreateCallBack.onFailed("createCaller failed : has call");
            return ;
        }

        String callId = getUserId() + System.currentTimeMillis();
        caller = new Caller(callId, calleeUserId, callType);
        callCreateCallBack.onSuccess(caller);
    }

    public Caller getCaller() {
        if (!hasCaller())
            return null;

        return caller;
    }

    private synchronized void createCallee(String callId, String callerUserId, CallType callType) {
        if (hasCall()) {
            return;
        }

        callee = new Callee(callId, callerUserId, callType);
        for (ICallIncomeListener callIncomeListener : callIncomeListeners) {
            callIncomeListener.onCallIncomingSuccess(callee);
        }
    }

    public Callee getCallee() {
        if (!hasCallee())
            return null;

        return callee;
    }

    public String getUserId() {
        // todo : 获取用户id
        return "用户id";
    }

    public void clearCall() {
        if (caller != null) {
            caller.clearCall();
            caller = null;
        }

        if (callee != null) {
            callee.clearCall();
            callee = null;
        }
    }
}
