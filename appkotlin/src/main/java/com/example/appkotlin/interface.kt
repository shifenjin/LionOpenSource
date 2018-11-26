package com.example.appkotlin

interface MustDoInterface {
    fun saveWorld(singleMothedInterface: SingleMothedInterface)
}

interface SingleMothedInterface {
    fun run(param: String)
}

interface BatManProxyInterface{
    fun print()
}

class BatManProxyImpl : BatManProxyInterface {
    override fun print() {
        print("代理方法调用")
    }

}