package com.example.lion_personal.lionopensource.annotation;

import android.util.Log;

import com.example.applibannotation.ApplicationOnCreate;

public class ApplicationInit {

    @ApplicationOnCreate
    public static void init() {
        Log.i(ApplicationInit.class.getSimpleName(), "ApplicationInit.class.getSimpleName() init() called");
    }
}
