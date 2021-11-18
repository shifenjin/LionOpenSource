package com.example.lion_personal.lionopensource.aop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lion_personal.lionopensource.R;
import com.example.lion_personal.lionopensource.aop.annotation.BehaviorTimeTrace;

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
