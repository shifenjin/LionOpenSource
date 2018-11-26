package com.example.appkotlin.coroutines

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.appkotlin.R
import com.example.appkotlin.tools.BaseActivity
import kotlinx.coroutines.*
import org.jetbrains.anko.info

class CoroutinesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        testCoroutine()
        info { "log" }

    }

    fun testCoroutine() {
        var coroutineJob = GlobalScope.launch {
            // 超时时间
            withTimeout(3500) {
                // 重复次数
                repeat(10) {
                    delay(1000)
                    info { "$it delay 1000 millisecond log" }
                }
            }

        }
    }
}
