package com.example.lion_personal.lionopensource.annotation;

import android.app.Application;
import android.util.Log;

import com.example.applib.moudule_applifecycle_helpr.IModuleAppLifeCycle;
import com.example.applibannotation.ModuleAppLifeCycle;

@ModuleAppLifeCycle
public class AppLifeCycle implements IModuleAppLifeCycle {

    @Override
    public void applicationOnCreate(Application application) {
        Log.i(AppLifeCycle.class.getSimpleName(), "AppLifeCycle applicationOnCreate called");
    }
}
