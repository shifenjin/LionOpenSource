package com.example.lion_personal.lionopensource;

import android.app.Application;

import com.example.applib.ApplicationOnCreateHelper;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationOnCreateHelper.init();
    }
}
