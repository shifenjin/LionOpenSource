package com.example.lion_personal.lionopensource;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class OkhttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 认证
                .authenticator(new Authenticator() {
                    @Nullable
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return null;
                    }
                })
                // 连接池
                .connectionPool(new ConnectionPool())

                // 超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

                .build();

        // 长连接
        okHttpClient.newWebSocket(new Request.Builder().build(), new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }
        });

        // 请求头
        Headers headers = new Headers.Builder()
                .add("name", "value")
                .build();

        // 请求实体
        RequestBody requestBody;
        requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),"json-content");
//        requestBody = new RequestBody() {
//            @Nullable
//            @Override
//            public MediaType contentType() {
//                return null;
//            }
//
//            @Override
//            public void writeTo(BufferedSink sink) throws IOException {
//
//            }
//        };
//        requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), "");

        // 请求
        Request request = new Request.Builder()
                // URL
                .url("www.baidu.com")
                // 请求头
                .headers(headers)
                // 缓存机制
//                .cacheControl(CacheControl.FORCE_NETWORK)
                .cacheControl(new CacheControl.Builder().onlyIfCached().build())
                // 请求方法
                .post(requestBody)
                .build();

        // 准备就绪的请求封装提
        Call call = okHttpClient.newCall(request);

        //  同步执行请求
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 异步执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        // 取消请求
        call.cancel();
    }
}
