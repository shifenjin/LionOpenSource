package com.example.appkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appkotlin.tools.BaseActivity
import kotlinx.android.synthetic.main.activity_class.*
import org.jetbrains.anko.info

class ClassActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)

        val batMan = BatMan.create()
        // 调用扩展方法
        batMan.extendedMethod()
        // 方法调用 - 显示参数
        batMan.fly(speed = 10, distance = 20)
        // 方法调用 - 可变数量参数
        batMan.eat("apple", "fish", "banana")
        // 高阶方法 - 单方法参数
        batMan.saveWorld("batMan and superMan") {
            info("$it saving world")

        }
        // 尾递归方法
        batMan.count(5)

        // 反射
        val batManClassJava = BatMan::class.java
        batManClassJava.fields
        batManClassJava.methods
        batManClassJava.annotations
        val batManClass = BatMan::class
        batManClass.annotations
    }
}
