package com.example.lion_personal.lionopensource.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lion_personal.lionopensource.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder> {

    private Context mContext;
    private List<RecyclerViewDataItem> mRecyclerViewDataItemList;

    public RecyclerViewAdapter(Context context, List<RecyclerViewDataItem> recyclerViewDataItemList) {
        mContext = context;
        mRecyclerViewDataItemList = recyclerViewDataItemList;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recycler_view_item, viewGroup, false);

        return new RecyclerViewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder recyclerViewViewHolder, int i) {
        ImageView imageView = recyclerViewViewHolder.itemView.findViewById(R.id.iv_icon);
//        Glide.with(mContext)
//                .load(mRecyclerViewDataItemList.get(i).getIconUrlStr())
//                .into(imageView);
        imageView.setImageResource(R.mipmap.zhaoliyin);

        imageView.setOnClickListener(v -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewDataItemList.size();
    }

    //
    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
