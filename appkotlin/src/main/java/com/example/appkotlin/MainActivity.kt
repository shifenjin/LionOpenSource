package com.example.appkotlin

import android.os.Bundle
import com.example.appkotlin.tools.BaseActivity
import org.jetbrains.anko.info
import java.util.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // break && continue
        testBreakAndContinue()


        // foreach
        testForeach()

        // 函数式表达式
        testChange()

        // 集合
        testCollection()

    }

    private fun testCollection() {
        val onlyReadList = listOf<Any>()
        val arrayList = mutableListOf<Any>()
        val linkedList = LinkedList<Any>()  // java调用

        val onlyReadMap = hashMapOf<Any, Any>()
        val hashMap = mutableMapOf<Any, Any>()
    }

    private fun testChange() {
        val list = mutableListOf(2, 1, 3, 5, 4)
        list
                // 过滤
                .filter { it.equals(3) }
                // 排序
                .sortedBy { it }
                .sortedWith(compareByDescending({it}))
                // 转换
                .map { it.toString() }
    }

    private fun testForeach() {
        val list = listOf(1, 2, 3, 4, 5)
        run loop@{
            list.forEach {
                if (it == 3)
                    return@forEach  // continue
                if (it == 5)
                    return@loop // break
                info { it }
            }
        }
    }

    private fun testBreakAndContinue() {
        label_for_i@ for (i in 1..10) {
            if (i == 5)
                break@label_for_i
            label_for_j@ for (j in 1..10) {
                if (j == 5)
                    continue@label_for_j
                info { "j = $j" }
            }
            info { "i = $i" }
        }
    }

}
