package com.example.appmouduleaccount.api.init;

import com.example.appmouduleaccount.api.init.domain.InitResult;

public interface IInitAccountCallBack {

    void onSuccess(InitResult initResult);

    void onError(InitResult initResult);

    void onProgress();
}
