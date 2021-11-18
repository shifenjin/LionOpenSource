package com.example.lion_personal.lionopensource;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.appmouduleaccount.test.TestAccountActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
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

        ThreadPoolExecutor threadPoolExecutor;

        View view;
        MotionEvent motionEvent;

        ViewGroup viewGroup;

        Activity activity;


        ViewGroup.LayoutParams layoutParams;
        LayoutInflater inflater = LayoutInflater.from(this);

        Context context;
        ContextWrapper contextWrapper;
        ContextThemeWrapper contextThemeWrapper;

        Intent intent;
//        intent.setDataAndType("", )
//        intent.setAction(Intent.Action)

        LifecycleOwner lifecycleOwner;

        Intent intent1 = new Intent(this, TestAccountActivity.class);
        startActivity(intent1);

        // test
//        ViewParent

        Map map;
        HashMap hashMap;
        PriorityQueue priorityQueue = new PriorityQueue();

//        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper() {
//            @Override
//            public void onCreate(SQLiteDatabase db) {
//                db.execSQL("");
//            }
//
//            @Override
//            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//            }
//        };
//        SQLiteDatabase writableDatabase = sqLiteOpenHelper.getWritableDatabase().insert();
//        sqLiteOpenHelper.getReadableDatabase();


        UriMatcher a;
        ContentResolver contentResolver;
//        registerReceiver();

//        unregisterComponentCallbacks();

        SharedPreferences sharedPreferences;
//        getSharedPreferences().

        ListView listView = new ListView(this);
        BaseAdapter baseAdapter1 = new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }
        };
        listView.setAdapter(baseAdapter1);
        baseAdapter1.notifyDataSetChanged();

//        listView.setOnItemClickListener();
//        listView.setDivider();
//        listView.setHorizontalScrollBarEnabled();
//        listView.setVerticalScrollBarEnabled();
//        listView.setSelection();
//        listView.addFooterView();

        RelativeLayout relativeLayout;
        ConstraintLayout constraintLayout;
        TabLayout tabLayout;
        ViewPager viewPager = new ViewPager(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        };
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                return false;
            }
        });

        Fragment fragment;
        Drawable drawable;
        ResolveInfo resolveInfo;
        Handler handler1 = new Handler();
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

    public String getValueByKey(String key, JsonObject jsonObject) {

        if (jsonObject == null)
            return null;

        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {

            JsonElement value = entry.getValue();

            if (entry.getKey().equals(key)) {
                return value.toString();
            }
            else if (value.isJsonNull()) {
                continue;
            }
            else if (value.isJsonArray()) {
                JsonArray jsonArray = value.getAsJsonArray();
                while (jsonArray.iterator().hasNext()) {
                    JsonElement next = jsonArray.iterator().next();

                    // 递归
                    return getValueByKey(key, next.getAsJsonObject());
                }
            }
            else if (value.isJsonObject()) {
                // 递归
                return getValueByKey(key, value.getAsJsonObject());
            }
        }

        return null;
    }
}
