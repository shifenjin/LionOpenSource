package com.example.lion_personal.lionopensource.recyclerview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
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
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, recyclerViewDataItemList);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        // 布局模式
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.VERTICAL));
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {});
        // 动画
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(null);
//        itemTouchHelper.
        // 设置 adapter
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               count();
            }
        });
        // 刷新数据
        recyclerViewAdapter.notifyDataSetChanged();


    }


}
