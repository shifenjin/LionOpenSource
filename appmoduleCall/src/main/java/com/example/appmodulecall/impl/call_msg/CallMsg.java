package com.example.appmodulecall.impl.call_msg;

import java.io.Serializable;
import java.util.Map;

public class CallMsg implements Serializable {

    private String callMsgType;
    private Map<String, String> callMsgData;

    public CallMsg(String callMsgType, Map callMsgData){
        this.callMsgType = callMsgType;
        this.callMsgData = callMsgData;
    }

    /**
     * 获取 呼叫消息类型
     *
     * @return
     */
    public String getCallMsgType() {
        return callMsgType;
    }

    /**
     * 获取 呼叫消息数据
     *
     * @return
     */
    public Map<String, String> getCallMsgData() {
        return callMsgData;
    }
}
