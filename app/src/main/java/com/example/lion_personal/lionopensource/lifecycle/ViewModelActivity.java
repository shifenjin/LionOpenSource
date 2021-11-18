package com.example.lion_personal.lionopensource.lifecycle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lion_personal.lionopensource.R;
import com.example.lion_personal.lionopensource.lifecycle.ui.viewmodel.ViewModelFragment;

public class ViewModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_model_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ViewModelFragment.newInstance())
                    .commitNow();
        }
    }
}
