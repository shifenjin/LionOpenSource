package com.example.appmodule;

import android.util.Log;

import com.example.applibannotation.ApplicationOnCreate;

public class ApplicationInitModule {

    @ApplicationOnCreate
    public static void initModule() {
        Log.i(ApplicationInitModule.class.getSimpleName(), "ApplicationInitModule.class.getSimpleName() init() called");
    }
}
