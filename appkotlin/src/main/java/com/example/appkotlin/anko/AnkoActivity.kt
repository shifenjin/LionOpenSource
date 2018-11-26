package com.example.appkotlin.anko

import android.os.Bundle
import com.example.appkotlin.R
import com.example.appkotlin.tools.BaseActivity
import org.jetbrains.anko.*

class AnkoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anko)

        testStartActivity()

//        testToast()

        // dialog
//        testDialog()

        // logging (类需要实现接口 AnkoLogger)
//        testLogging()

        // dimension
//        testDimension()

    }

    private fun testDimension() {
        // px to dip
        px2dip(5)

        // dip to px
        dip(5)
    }

    private fun testLogging() {
        // debug
        debug("debug")
        // info
        info { "info" }
        // warn
        warn { "warn" }
        // error
        error { "error" }
    }

    private fun testDialog() {
        alert {
            title = "标题"
            message = "内容"
            // icon
            iconResource = R.mipmap.ic_launcher
            positiveButton("确定") {toast("点击了确定")}
            negativeButton("取消") {toast("点击了取消")}
//            yesButton { }
//            noButton {  }
//            cancelButton { toast("cancel") }
        }.show()

        alert {
            // 自定义标题控件
            customTitle{}
            // 自定义内容控件
            customView{}
        }
    }

    private fun testToast() {
        // toast
        // long toast
        toast("短的toast")
        longToast("长的toast")
    }

    /**
     * test startActivity
     */
    private fun testStartActivity() {
//        testStartActivity()
//        startActivity<MainActivity>()
//        startActivity(intentFor<MainActivity>().singleTop())
//        startActivity(intentFor<MainActivity>("id_key" to "id_value").newTask())
        // 网页
        browse("www.baidu.com")
    }
}
