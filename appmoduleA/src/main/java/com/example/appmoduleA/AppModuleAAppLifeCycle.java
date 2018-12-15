package com.example.appmoduleA;

import android.util.Log;

import com.example.applib.moudule_applifecycle_helpr.IModuleAppLifeCycle;
import com.example.applibannotation.ModuleAppLifeCycle;

@ModuleAppLifeCycle
public class AppModuleAAppLifeCycle implements IModuleAppLifeCycle {

    @Override
    public void applicationOnCreate() {
        Log.i(AppModuleAAppLifeCycle.class.getSimpleName(), "AppModuleAAppLifeCycle - applicationOnCreate - called");
    }
}
