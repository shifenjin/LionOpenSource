package com.example.lion_personal.lionopensource;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.applib.moudule_applifecycle_helpr.ModuleAppLifeCycleHelper;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);

        long start = System.currentTimeMillis();
        ModuleAppLifeCycleHelper.getInstance().onCreate();
        Log.i(AppApplication.class.getSimpleName(), String.valueOf(System.currentTimeMillis() - start));

        View view;
        ViewGroup viewGroup;
        BaseAdapter baseAdapter;

    }
}
