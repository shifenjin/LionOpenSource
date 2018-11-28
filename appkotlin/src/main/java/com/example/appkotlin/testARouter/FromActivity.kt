package com.example.appkotlin.testARouter

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.appkotlin.R
import com.example.appkotlin.tools.BaseActivity
import kotlinx.android.synthetic.main.activity_from.*
import java.io.Serializable

@Route(path = RouterMap.APPKOTLIN_FROM_ACTIVITY)
class FromActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from)

        btn.setOnClickListener {
            // navigate activity
            ARouter.getInstance().build(RouterMap.APPKOTLIN_TO_ACTIVITY)
                    .withSerializable("key", ArgObject(1, "lion"))
                    .navigation(this, 1, object : NavigationCallback{
                        override fun onLost(postcard: Postcard?) {
                        }

                        override fun onFound(postcard: Postcard?) {
                        }

                        override fun onInterrupt(postcard: Postcard?) {
                        }

                        override fun onArrival(postcard: Postcard?) {
                        }
                    })

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }
}

data class ArgObject(var id: Int, var name: String) : Serializable
