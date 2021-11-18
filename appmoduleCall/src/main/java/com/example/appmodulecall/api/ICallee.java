package com.example.appmodulecall.api;

import android.content.Context;

public interface ICallee extends ICall {

    /**
     * 接听 呼入
     */
    void answerCall(Context context);
}
