package com.example.appkotlin.coroutines

import android.os.Bundle
import android.util.Log
import com.example.appkotlin.R
import com.example.appkotlin.tools.BaseActivity
import kotlinx.coroutines.*
import org.jetbrains.anko.info
import kotlin.coroutines.EmptyCoroutineContext

class CoroutinesActivity : BaseActivity() {

    val TAG = "CoroutinesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)


//        testPrintCoroutineThreadId()

//        testCoroutine()
//        switchCoroutine()
        coroutine_100000()
    }

    private fun switchCoroutine() {
        GlobalScope.launch(Dispatchers.Main) {
//            threadWithThreadName()
            withContext(Dispatchers.IO) {
                // do something in IO thread
            }
            mainWithThreadName()
        }
    }

    private fun coroutine_100000() {
        repeat(100000) {
            GlobalScope.launch {
                delay(1000)
                Log.i("test", "$it  ${Thread.currentThread()}")
            }
        }
    }

    private fun testCoroutine() {
        GlobalScope.launch() {
            delay(1000)
            coroutine()
        }

        main()
    }

    private fun testRunBlocking() {
        runBlocking(Dispatchers.Main) {
//            launch {
//                method()
//            }
            Log.i("haha", "launch ${Thread.currentThread()}")
        }
        Log.i("haha", "main ${Thread.currentThread()}")
    }

    suspend fun method() {
        Log.i("haha", "method ${Thread.currentThread()}")
    }

    fun testPrintCoroutineThreadId() {
        repeat(3) {
            GlobalScope.launch() {
                Log.i("haha", "threadId = ${Thread.currentThread()}")
            }
        }
    }

    fun async() {
        GlobalScope.launch(Dispatchers.Main) {
            val a1 = async(Dispatchers.IO) { a1() }
            val a2 = async(Dispatchers.IO) { a2() }
            val result = a1.await() + a2.await()

            // show result
        }
    }

    private fun a2() : Int{
        TODO("Not yet implemented")
    }

    private fun a1() : Int {
        TODO("Not yet implemented")
    }

    fun testWithoutTimeOrNull() {
        GlobalScope.launch {
            val result = withTimeoutOrNull(3000L) {
                repeat(5) { i ->
                    println("I'm sleeping $i ...")
                    delay(1000L)
                }
                "Done" // cannel before get result
            }

            if (result != null) {
                // 任务成功
            } else {
                // 异常处理
            }
        }

        GlobalScope.launch {
            try {
                withTimeout(3000L) {
                    repeat(5) { i ->
                        println("I'm sleeping $i ...")
                        delay(1000L)
                    }
                    "Done" // cannel before get result
                }
            } catch (e : TimeoutCancellationException) {
                // 异常处理

                return@launch
            }

            // 任务成功
        }
    }

    fun testCoroutineWithoutTime() {
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

        runBlocking {
            var job = launch {

            }

            var async = async { }

            val jobs = List(1000) {
                launch(EmptyCoroutineContext, CoroutineStart.LAZY) { }
            }
            jobs.forEach { it.join() }
        }
    }

    fun testSuspend() {
        GlobalScope.launch {
            val token = getToken()
            val userInfo = getUserInfo(token)
            setUserInfo(userInfo)
        }
        repeat(8) {
            Log.e(TAG, "主线程执行$it")
        }
    }

    private fun setUserInfo(userInfo: String) {
        Log.e(TAG, userInfo)
    }

    private suspend fun getToken(): String {
        delay(2000)
        return "token"
    }

    private suspend fun getUserInfo(token: String): String {
        delay(2000)
        return "$token - userInfo"
    }

    private fun main() {
        Log.i("test", "call main.  ")
    }

    private fun coroutine() {
        Log.i("test", "call coroutine.   ")
    }

    private fun mainWithThreadName() {
        Log.i("test", "call main.  ${Thread.currentThread()}")
    }

    private fun coroutineWithThreadName() {
        Log.i("test", "call thread.   ${Thread.currentThread()}")
    }

    private suspend fun threadWithThreadName() = withContext(Dispatchers.IO) {
        Log.i("test", "call coroutine.   ${Thread.currentThread()}")
    }
}
