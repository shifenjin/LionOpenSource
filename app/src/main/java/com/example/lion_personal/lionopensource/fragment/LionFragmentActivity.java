package com.example.lion_personal.lionopensource.fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lion_personal.lionopensource.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class LionFragmentActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lion_fragment);

        CustomFragment customFragment = new CustomFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_flagment_custom, customFragment);
        fragmentTransaction.commit();
    }
}
