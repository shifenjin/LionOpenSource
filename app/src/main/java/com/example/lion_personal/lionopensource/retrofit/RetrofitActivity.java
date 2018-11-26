package com.example.lion_personal.lionopensource.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lion_personal.lionopensource.R;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        Retrofit retrofit = new Retrofit.Builder()
                // 基础URL
                .baseUrl("baseUrl")
                // 数据转换
                .addConverterFactory(GsonConverterFactory.create())
                // 网络请求适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitApi api = retrofit.create(RetrofitApi.class);
        api.method("path", new RetrofitReq())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(retrofitResp -> {}, throwable -> {});
    }
}
