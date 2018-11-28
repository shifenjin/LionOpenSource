package com.example.appkotlin.testARouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

@Interceptor(priority = 1)
class CustomInterceptor : IInterceptor, AnkoLogger{
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        info { "CustomInterceptor process" }
        if (RouterMap.APPKOTLIN_TO_ACTIVITY.equals(postcard?.path)) {
            info { "CustomInterceptor onInterrupt" }
            callback?.onInterrupt(null)
            return
        }
        info { "CustomInterceptor onContinue" }
        callback?.onContinue(postcard)
    }

    override fun init(context: Context?) {
        info { "CustomInterceptor init" }
    }
}