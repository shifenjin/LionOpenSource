package com.example.lion_personal.lionopensource.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lion_personal.lionopensource.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        // 数据
        List<RecyclerViewDataItem> recyclerViewDataItemList= new ArrayList<>();

        String imageUrlChuQiao = "http://img3.imgtn.bdimg.com/it/u=2355838321,3868809210&fm=26&gp=0.jpg";
        for (int i = 0; i < 20; i++) {
            RecyclerViewDataItem recyclerViewDataItem = new RecyclerViewDataItem();
            recyclerViewDataItem.setIconUrlStr(imageUrlChuQiao);
            recyclerViewDataItemList.add(recyclerViewDataItem);
        }

        // Adapter
//        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, recyclerViewDataItemList);

//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        // 布局模式
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 分割线
//        recyclerView.addItemDecoration();
        // 设置 adapter
//        recyclerView.setAdapter(recyclerViewAdapter);
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               count();
//            }
//        });
        // 刷新数据
//        recyclerViewAdapter.notifyDataSetChanged();


    }


}
