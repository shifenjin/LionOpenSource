package com.example.appmodulecall.impl.call_msg;

import androidx.annotation.NonNull;

import com.example.appmodulecall.impl.Call;
import com.example.appmodulecall.impl.CallManager;
import com.example.appmodulecall.impl.Callee;
import com.example.appmodulecall.impl.Caller;

import java.util.Map;


/**
 * 呼叫消息 管理器
 * <p>
 * 负责 呼叫消息的发送及接收
 */
public class CallMsgManager {

    /**
     * 发送 呼叫消息
     *
     * @param callMsg
     */
    public static void sendCallMsg(Call call, CallMsg callMsg) {

        if (call == null) {
            // todo -> error
            return;
        }

        if (callMsg == null) {
            // todo -> error
            return;
        }

        Map<String, String> callMsgData = callMsg.getCallMsgData();
        callMsgData.put(CallMsgKey.KEY_CALL_ID, call.getCallId());
        callMsgData.put(CallMsgKey.KEY_CALL_TYPE, call.getCallType().getValue());
        callMsgData.put(CallMsgKey.KEY_CALLER_USER_ID, call.getCallerUserId());
        callMsgData.put(CallMsgKey.KEY_CALLEE_USER_ID, call.getCalleeUserId());

        // todo -> send msg to server
    }

    /**
     * 处理 呼叫消息
     *
     * @param callMsg
     */
    // todo 放入系统消息监听中
    public static void handleCallMsg(@NonNull CallMsg callMsg) {

        String callMsgType = callMsg.getCallMsgType();
        switch (callMsgType) {
            // caller - sdp邀请
            case CallMsgType.SEND_OFFER_SDP: {
                CallManager.getInstance().onCallIncome(callMsg);
                break;
            }
            // callee - sdp回应
            case CallMsgType.SEND_ANSWER_SDP: {
                Caller caller = CallManager.getInstance().getCaller();
                if (caller != null) {
                    caller.onCallAnswer(callMsg);
                }
            }
            // callee - 收到呼入方发送的iceCandidate
            case CallMsgType.SEND_ICE_CANDIDATE: {
                Callee callee = CallManager.getInstance().getCallee();
                if (callee != null) {
                    callee.onAddCandidate(callMsg);
                }
            }
            // 挂断
            case CallMsgType.SEND_HANG_UP: {
                String callId = callMsg.getCallMsgData().get(CallMsgKey.KEY_CALL_ID);

                Caller caller = CallManager.getInstance().getCaller();
                if (caller != null)
                    if (caller.getCallId() != null && caller.getCallId().equals(callId))
                        caller.onHangup();


                Callee callee = CallManager.getInstance().getCallee();
                if (callee != null)
                    if (callee.getCallId() != null && callee.getCallId().equals(callId))
                        callee.onHangup();
            }
            // 异常结束
            case CallMsgType.SEND_EXCEPTION_END: {
                String callId = callMsg.getCallMsgData().get(CallMsgKey.KEY_CALL_ID);
                Caller caller = CallManager.getInstance().getCaller();
                if (caller != null)
                    if (caller.getCallId() != null && caller.getCallId().equals(callId))
                        caller.onEndForRemoteException(callMsg);


                Callee callee = CallManager.getInstance().getCallee();
                if (callee != null)
                    if (callee.getCallId() != null && callee.getCallId().equals(callId))
                        callee.onEndForRemoteException(callMsg);

            }
        }
    }
}
