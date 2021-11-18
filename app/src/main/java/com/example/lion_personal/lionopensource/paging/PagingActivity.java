package com.example.lion_personal.lionopensource.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.lion_personal.lionopensource.R;

public class PagingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = new RecyclerView(this);
        PagedListAdapter pagedListAdapter = new PagingAdapter();


        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource create() {
                PagingDataSource pagingDataSource = new PagingDataSource();
                return pagingDataSource;
            }
        };
        PagedList.Config.Builder builder = new PagedList.Config.Builder();
        builder.setPageSize(15)
                .setEnablePlaceholders(true)
                .setPrefetchDistance(30)
                .build();
        LiveData<PagedList<String>> liveData = new LivePagedListBuilder(factory, 20).build();
        liveData.observe(this, o -> {
            pagedListAdapter.submitList(o);
        });
    }

}
