package com.example.lion_personal.lionopensource.annotation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.applib.BindView;
import com.example.applibannotation.CustomBindViewAnnotation;
import com.example.lion_personal.lionopensource.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class AnnotationActivity extends RxAppCompatActivity {

    @CustomBindViewAnnotation(viewId = R.id.btn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotation);

        BindView.bind(this);

        button.setOnClickListener(view -> Toast.makeText(this, "CustomBindView worked!!", Toast.LENGTH_SHORT).show());
    }
}
