package com.example.appkotlin.testClass

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

// 抽象类
abstract class Person constructor(name: String) : AnkoLogger{

    protected lateinit var mName: String

    init {
        mName = name
    }

    open fun walk() {
        info { "Person walk" }
    }

    abstract fun fight()
}

// 实现类
class BatMan : Person {
    // 伴生
    companion object {
        // 伴生属性
        const val defaultName = "BatMan"
        // 伴生方法
        fun create(name: String = defaultName) : BatMan = BatMan(name)
    }

    // 成员变量
    var weapon: String? = null
        get() {return weapon}

    // 构造方法
    private constructor(name: String) : super(name) {
    }

    override fun walk() {
        info { "BatMan walk" }
    }

    override fun fight() {
        info { "BatMan fight" }
    }

    fun fly(speed: Int = 5, distance: Int, height: Int = 10) {
        info { "BatMan fly : speed = $speed, distance = $distance, height = $height" }
    }

    fun eat(vararg food: String){
        for (foodItem in food)
            info { "BatMan eat : $foodItem" }
    }

    // 尾递归方法
    tailrec fun count(num: Int): Int {
        if (num < 0)
            return 0

        if (num == 0)
            return num
        else
            return count(num - 1)
    }

    fun saveWorld(who: String, run : (who: String) -> Unit) {
        info("before save world")
        run.invoke(who)
        info("after save world")
    }

}

// 扩展方法
fun BatMan.extendedMethod() {
    info { "invoke BatMan`s extendedMethod" }
}

// 伴生类
object CompanionClass {

}

