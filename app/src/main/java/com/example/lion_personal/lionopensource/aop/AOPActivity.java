package com.example.lion_personal.lionopensource.aop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lion_personal.lionopensource.R;
import com.example.lion_personal.lionopensource.aop.annotation.BehaviorTimeTrace;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AOPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop);

        testBehaviorTimeTrace();
    }

    @BehaviorTimeTrace
    private void testBehaviorTimeTrace() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
