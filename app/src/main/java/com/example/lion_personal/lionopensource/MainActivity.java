package com.example.lion_personal.lionopensource;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread;
        ThreadLocal threadLocal;
        Handler handler;

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future;
        future = executorService.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return null;
            }
        });
        try {
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Executor executor;
        FutureTask futureTask;

        AsyncTask asyncTask;

        ThreadPoolExecutor threadPoolExecutor;

        View view;
        MotionEvent motionEvent;

        ViewGroup viewGroup;

        Activity activity;


        ViewGroup.LayoutParams layoutParams;
        LayoutInflater inflater = LayoutInflater.from(this);

        ListView listView;
        BaseAdapter baseAdapter;

        RecyclerView recyclerView;

        Context context;
        ContextWrapper contextWrapper;
        ContextThemeWrapper contextThemeWrapper;

        Intent intent;
//        intent.setDataAndType("", )
//        intent.setAction(Intent.Action)

        SQLiteOpenHelper sqLiteOpenHelper;
        LifecycleOwner lifecycleOwner;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

//    class customView extends View {
//
//        public customView(Context context) {
//            super(context);
//        }
//
//        public customView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
//            super(context, attrs);
//        }
//
//        public customView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
//            super(context, attrs, defStyleAttr);
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//        public customView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//            super(context, attrs, defStyleAttr, defStyleRes);
//        }
//
//        @Override
//        protected void onFinishInflate() {
//            super.onFinishInflate();
//
//
//        }
//
//        @Override
//        protected void onAttachedToWindow() {
//            super.onAttachedToWindow();
//        }
//
//        @Override
//        protected void onDetachedFromWindow() {
//            super.onDetachedFromWindow();
//        }
//
//
//    }


}
