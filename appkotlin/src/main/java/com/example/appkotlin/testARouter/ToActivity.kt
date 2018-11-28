package com.example.appkotlin.testARouter

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.appkotlin.R
import com.example.appkotlin.tools.BaseActivity
import org.jetbrains.anko.info

@Route(path = RouterMap.APPKOTLIN_TO_ACTIVITY)
class ToActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to)

        info { intent.getSerializableExtra("key") }
    }
}
