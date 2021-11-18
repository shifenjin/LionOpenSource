package com.example.lion_personal.lionopensource.paging;

import androidx.paging.ItemKeyedDataSource;
import androidx.annotation.NonNull;

public class PagingDataSource extends ItemKeyedDataSource {
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    @NonNull
    @Override
    public Object getKey(@NonNull Object item) {
        return null;
    }
}
