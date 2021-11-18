package com.example.lion_personal.lionopensource.lifecycle.ui.viewmodel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lion_personal.lionopensource.R;

public class ViewModelFragment extends Fragment {

    private CustomViewModel mViewModel;

    public static ViewModelFragment newInstance() {
        return new ViewModelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_model_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.customLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                
            }
        });
    }


}
