package com.example.appkotlin.web

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appkotlin.R
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.coroutines.delay
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONObject

open class WebActivity : AppCompatActivity() {

    val TAG = "WebActivity"

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        btn_call_js.onClick {
//            webView?.loadUrl("javascript:callJS()")
            val jsFileName = "javascript"
            val funcName = "callJS"
            val param = "123456"

            // webview 调 js
            webView?.evaluateJavascript("$jsFileName:$funcName($param)", object : ValueCallback<String> {
                override fun onReceiveValue(value: String?) {
                    Log.i(TAG, "onReceiveValue: $value")
                }
            })
        }

        createWebView()

        configWebView()

        configWebViewJavascript()

        //        webView.loadUrl("https://www.baidu.com")
        webView?.loadUrl("https://blog.csdn.net/qq_34584049/article/details/78280815")


//        webView?.loadUrl("javascript:callJS()");
//        webView?.evaluateJavascript("javascript:callJS()", object : ValueCallback<String> {
//            override fun onReceiveValue(value: String?) {
//                Log.i(TAG, "onReceiveValue: $value")
//            }
//        })

        // add javascript interface
//        webView?.addJavascriptInterface(WebViewInterface(this), "test")

//        webView?.loadUrl("file:///android_asset/javascript.html");

    }

    private fun configWebViewJavascript() {
        // javascript setting
        webView?.settings?.apply {

            //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
            // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
            // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true    //  /支持通过JS打开新窗口
        }
    }

    private fun configWebView() {
        webView?.settings?.apply {
            //支持插件
            //            setPluginsEnabled(true);

            //设置自适应屏幕，两者合用
            useWideViewPort = true //将图片调整到适合webview的大小
            loadWithOverviewMode = true // 缩放至屏幕的大小

            //缩放操作
            setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
            setDisplayZoomControls(false); //隐藏原生的缩放控件

            // 缓存
            cacheMode = WebSettings.LOAD_NO_CACHE //关闭webview中缓存
            //缓存模式如下：
            //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
            //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
            //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
            //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

            // 安全漏洞方案
            savePassword = false //不保存密码

            //其他细节操作
            allowFileAccess = true //设置可以访问文件
            loadsImagesAutomatically = true; //支持自动加载图片
            defaultTextEncodingName = "utf-8" //设置编码格式
        }

        // web view client
        webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                // web页面加载前回调
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                // web页面加载完回调
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)

                // web页面每一次加载资源时回调
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

                // web页面加载出错时回调
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)

                // ??

                //                handler.proceed() //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }
        }

        // web chrome client
        webView?.webChromeClient = object : WebChromeClient() {
            // 获得网页的加载进度并显示
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                Log.i(TAG, "onProgressChanged : $newProgress %")
            }

            // 获取Web页中的标题
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)

                Log.i(TAG, "onReceivedTitle : $title ")
            }

            // 打开文件选择器的回调（比如：打开相册、启动拍照或打开本地文件管理器）
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)

                Log.i(TAG, "onShowFileChooser: ")
            }
            // 获取javascript的警告
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {

                Log.i(TAG, "onJsAlert : $message ")

                // 展示警告提示框
                AlertDialog.Builder(this@WebActivity)
                    .setTitle("JsAlert")
                    .setMessage(message)
                    .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            result!!.confirm()
                        }
                    })
                    .setCancelable(false)
                    .show()
                return true

                //                return super.onJsAlert(view, url, message, result)
            }
        }
    }

    private fun createWebView() {
        // 初始化webView
        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        webView = WebView(applicationContext)
        webView?.layoutParams = params
        webViewLayout.addView(webView)
    }

    override fun onDestroy() {
        // 销毁webview
        webView?.apply {
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            clearHistory()
            (getParent() as ViewGroup).removeView(this)
            destroy()
        }
        webView = null

        super.onDestroy()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView?.canGoBack() == true) {
                webView?.goBack()
                Log.i(TAG, "webView.goBack()")
                return true
            }
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }
}