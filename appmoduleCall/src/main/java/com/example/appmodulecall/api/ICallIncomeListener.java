package com.example.appmodulecall.api;

public interface ICallIncomeListener {

    /**
     *  呼叫呼入 成功
     */
    void onCallIncomingSuccess(ICallee callee);

    /**
     *  呼叫呼入 失败
     */
    void onCallIncomingFailed(String reason);
}
