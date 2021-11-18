package com.example.appmodulecall.impl;

import android.widget.RelativeLayout;

import com.example.appmodulecall.api.ICallLifeCycleListener;
import com.example.appmodulecall.impl.call_error.CallException;

import java.util.ArrayList;
import java.util.List;

/**
 * 呼叫 生命周期 处理器
 */
public class CallLifeCycleHandler {

    private List<ICallLifeCycleListener> callLifeCycleListeners;

    public CallLifeCycleHandler() {
        callLifeCycleListeners = new ArrayList<>();
    }

    public void addCallLifeCycleListener(ICallLifeCycleListener callLifeCycleListener) {
        callLifeCycleListeners.add(callLifeCycleListener);
    }

    public void removeCallLifeCycleListener(ICallLifeCycleListener callLifeCycleListener) {
        callLifeCycleListeners.remove(callLifeCycleListener);
    }

    public void notifyCallViewCreated(RelativeLayout callView) {
        for (ICallLifeCycleListener callLifeCycleListener : callLifeCycleListeners) {
            callLifeCycleListener.onCallViewCreated(callView);
        }
    }

    public void notifyCallConnecting() {
        for (ICallLifeCycleListener callLifeCycleListener : callLifeCycleListeners) {
            callLifeCycleListener.onCallConnecting();
        }
    }

    public void notifyCallConnected() {
        for (ICallLifeCycleListener callLifeCycleListener : callLifeCycleListeners) {
            callLifeCycleListener.onCallConnected();
        }
    }

    public void notifyCallOnHangUp() {
        for (ICallLifeCycleListener callLifeCycleListener : callLifeCycleListeners) {
            callLifeCycleListener.onHangUp();
        }
    }

    public void notifyCallException(String description) {
        for (ICallLifeCycleListener callLifeCycleListener : callLifeCycleListeners) {
            callLifeCycleListener.onEndForException(description);
        }
    }
}
