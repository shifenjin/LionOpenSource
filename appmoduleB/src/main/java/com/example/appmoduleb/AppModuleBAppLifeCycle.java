package com.example.appmoduleb;

import android.app.Application;
import android.util.Log;

import com.example.applib.moudule_applifecycle_helpr.IModuleAppLifeCycle;
import com.example.applibannotation.ModuleAppLifeCycle;

@ModuleAppLifeCycle
public class AppModuleBAppLifeCycle implements IModuleAppLifeCycle {
    @Override
    public void applicationOnCreate(Application application) {
        Log.i(AppModuleBAppLifeCycle.class.getSimpleName(), "AppModuleBAppLifeCycle - applicationOnCreate - called");
    }
}
