package com.example.appmodulecall.impl.call_msg;

public class CallMsgType {
    // caller - sdp邀请
    public final static String SEND_OFFER_SDP = "send_offer_sdp";
    // callee - sdp回应
    public final static String SEND_ANSWER_SDP = "send_answer_sdp";
    // 发送 iceCandidate
    public final static String SEND_ICE_CANDIDATE = "send_ice_candidate";
    // 挂断
    public final static String SEND_HANG_UP = "send_hang_up";
    // 异常结束
    public final static String SEND_EXCEPTION_END = "send_exception_end";
}
