package com.example.appkotlin

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class AppKotlinApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initARouter()
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}
