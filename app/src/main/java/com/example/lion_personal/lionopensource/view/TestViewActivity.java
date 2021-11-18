package com.example.lion_personal.lionopensource.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lion_personal.lionopensource.R;
import com.example.lion_personal.lionopensource.view.ui.testview.TestViewFragment;

public class TestViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TestViewFragment.newInstance())
                    .commitNow();
        }
    }
}
