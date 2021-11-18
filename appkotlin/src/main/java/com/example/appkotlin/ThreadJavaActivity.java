package com.example.appkotlin;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ThreadJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        testThread();
            switchThread();
//        thread_100000();

    }

    private void thread_100000() {

        int i = 100000;

        while (i > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            i--;
        }
    }

    private void switchThread() {
        Handler mainHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadWithThreadName();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainWithThreadName();
                    }
                });
            }
        }).start();
    }

    private void testThread() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                thread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        main();
    }

    private void main() {
        Log.i("test", "call main.  ");
    }

    private void thread() {
        Log.i("test", "call thread.   ");
    }

    private void mainWithThreadName() {
        Log.i("test", "call main.  " + Thread.currentThread());
    }

    private void threadWithThreadName() {
        Log.i("test", "call thread.   " + Thread.currentThread());
    }

}
