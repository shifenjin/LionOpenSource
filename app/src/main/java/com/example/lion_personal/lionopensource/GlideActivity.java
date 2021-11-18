package com.example.lion_personal.lionopensource;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class GlideActivity extends AppCompatActivity {

    private ImageView testImageView;
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        testImageView = findViewById(R.id.iv_test);

        testButton = findViewById(R.id.btn_test);
        testButton.setOnClickListener(v -> {
            String test_png_str = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
            String test_gif_str = "http://p1.pstatp.com/large/166200019850062839d3";

            RequestOptions requestOptions = new RequestOptions();
            requestOptions
                    // 禁用内存缓存
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

                    // 图片大小
                    .override(Target.SIZE_ORIGINAL)
                    // 图片变换
                    .transform(new RoundedCorners(12))
//                    .transform(new CircleCrop())
//                    .circleCrop()

                    // 占位图
                    .placeholder(R.mipmap.ic_launcher)
                    // 异常占位图
                    .error(R.mipmap.ic_error)

                    // 签名
                    .signature(new ObjectKey(System.currentTimeMillis()))
            ;
            Glide.with(this)
                    // 读取资源
                    .load(test_gif_str)
//                    .load(new GlideUrl(test_gif_str))
                    // 显示动画
                    .transition(DrawableTransitionOptions.withCrossFade())
                    // 配置
                    .apply(requestOptions)
                    // 监听加载生命周期
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    // 加载
                    .into(testImageView);
//                    .into(new SimpleTarget<Drawable>() {
//                        @Override
//                        public void onLoadStarted(@Nullable Drawable placeholder) {
//                            super.onLoadStarted(placeholder);
//                        }
//
//                        @Override
//                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//
//                        }
//                    });


                    // 预加载
//                    .preload();


                    // 预加载（获取预加载任务）
            FutureTarget<File> fileFutureTarget = Glide.with(this).asFile().load(test_gif_str)
                    .submit();
            try {
                File file = fileFutureTarget.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        Bitmap bitmap;

    }
}
