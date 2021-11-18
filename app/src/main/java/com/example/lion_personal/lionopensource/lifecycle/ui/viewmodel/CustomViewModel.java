package com.example.lion_personal.lionopensource.lifecycle.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

public class CustomViewModel extends AndroidViewModel {

    public LiveData<String> customLiveData;

    public CustomViewModel(@NonNull Application application) {
        super(application);
    }

}
