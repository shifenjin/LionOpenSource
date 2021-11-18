package com.example.appmodulecall.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.appmodulecall.impl.CallServiceImpl;
import com.example.appmodulecall.R;
import com.example.appmodulecall.api.CallType;
import com.example.appmodulecall.api.ICallCreateCallBack;
import com.example.appmodulecall.api.ICallEffectHandler;
import com.example.appmodulecall.api.ICallIncomeListener;
import com.example.appmodulecall.api.ICallLifeCycleListener;
import com.example.appmodulecall.api.ICallService;
import com.example.appmodulecall.api.ICallee;
import com.example.appmodulecall.api.ICaller;

public class TestCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_call);

        call();
        callIncome();

    }

    private void callIncome() {
        ICallService callService = new CallServiceImpl();

        callService.addCallIncomeListener(new ICallIncomeListener() {
            @Override
            public void onCallIncomingSuccess(final ICallee callee) {
                // todo (such as start call activity)

                callee.addCallLifeCycleListener(new ICallLifeCycleListener() {
                    @Override
                    public void onCallViewCreated(RelativeLayout callView) {
                        // todo (deal with call view)

                        callee.answerCall(TestCallActivity.this);
                    }

                    @Override
                    public void onCallConnecting() {

                    }

                    @Override
                    public void onCallConnected() {

                    }

                    /**
                     * 被挂断
                     */
                    @Override
                    public void onHangUp() {

                    }

                    /**
                     * 异常结束
                     *
                     * @param reason
                     */
                    @Override
                    public void onEndForException(String reason) {

                    }

                });
                callee.createCallView(TestCallActivity.this);
            }

            @Override
            public void onCallIncomingFailed(String reason) {

            }
        });
    }

    private void call() {
        ICallService callService = new CallServiceImpl();

        callService.createCall("calleeUserId", CallType.VIDEO, new ICallCreateCallBack() {
            @Override
            public void onSuccess(final ICaller caller) {
                // todo (such as start call activity)

                caller.addCallLifeCycleListener(new ICallLifeCycleListener() {

                    @Override
                    public void onCallViewCreated(RelativeLayout callView) {
                        // todo (deal with call view)

                        caller.startCall(TestCallActivity.this);
                    }

                    @Override
                    public void onCallConnecting() {

                    }

                    @Override
                    public void onCallConnected() {
                        // todo (say something)

                        // todo (try some call effects)
                        ICallEffectHandler callEffectHandler = caller.getCallEffectHandler();

                        caller.hangup();
                    }

                    /**
                     * 被挂断
                     */
                    @Override
                    public void onHangUp() {

                    }

                    /**
                     * 异常结束
                     *
                     * @param reason
                     */
                    @Override
                    public void onEndForException(String reason) {

                    }

                });
                caller.createCallView(TestCallActivity.this);
            }

            @Override
            public void onFailed(String reason) {

            }
        });
    }
}
