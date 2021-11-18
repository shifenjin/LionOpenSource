package com.example.lion_personal.lionopensource.paging;

import androidx.paging.PagedListAdapter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lion_personal.lionopensource.R;

public class PagingAdapter extends PagedListAdapter<String, PagingAdapter.PagingViewHolder> {

    static DiffUtil.ItemCallback<String> itemCallback = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String s, @NonNull String t1) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull String s, @NonNull String t1) {
            return false;
        }
    };

    protected PagingAdapter() {
        super(itemCallback);
    }

    @NonNull
    @Override
    public PagingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recycler_view_item, viewGroup, false);

        return new PagingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PagingViewHolder recyclerViewViewHolder, int i) {

    }

    public class PagingViewHolder extends RecyclerView.ViewHolder {

        public PagingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
