package com.example.lion_personal.lionopensource.data_structure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.lion_personal.lionopensource.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class DataStructureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_structure);

        // 数组
        String[] stringArray = new String[5];
        int length = stringArray.length;

        List list = new LinkedList();



        // 阻塞队列
        // 优先队列
        PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();
        priorityBlockingQueue.put(new Comparable<Integer>() {
            @Override
            public int compareTo(Integer o) {
                return 0;
            }
        });
        // 延迟队列
        DelayQueue delayQueue = new DelayQueue();
        delayQueue.put(new Delayed() {
            @Override
            public long getDelay(TimeUnit unit) {
                return 0;
            }

            @Override
            public int compareTo(Delayed o) {
                return 0;
            }
        });
        // 无容量队列
        SynchronousQueue synchronousQueue = new SynchronousQueue();


        // 哈希表
        TreeMap treeMap = new TreeMap();

        String a = "aaa";
    }
}
