package com.example.appmodulecall.impl;

import com.example.appmodulecall.api.CallType;
import com.example.appmodulecall.api.ICallCreateCallBack;
import com.example.appmodulecall.api.ICallIncomeListener;
import com.example.appmodulecall.api.ICallService;
import com.example.appmodulecall.api.ICallee;
import com.example.appmodulecall.api.ICaller;

public class CallServiceImpl implements ICallService {
    @Override
    public void addCallIncomeListener(ICallIncomeListener callIncomeListener) {
        CallManager.getInstance().addCallIncomeListener(callIncomeListener);
    }

    @Override
    public void removeCallIncomeListener(ICallIncomeListener callIncomeListener) {
        CallManager.getInstance().removeCallIncomeListener(callIncomeListener);
    }

    @Override
    public void createCall(String calleeUserId, CallType callType, ICallCreateCallBack callCreateCallBack) {
        CallManager.getInstance().createCaller(calleeUserId, callType, callCreateCallBack);
    }

    /**
     * 获取 主动呼叫
     *
     * @return
     */
    @Override
    public ICaller getCaller() {
        return CallManager.getInstance().getCaller();
    }

    /**
     * 获取 被动呼叫
     *
     * @return
     */
    @Override
    public ICallee getCallee() {
        return CallManager.getInstance().getCallee();
    }


}
