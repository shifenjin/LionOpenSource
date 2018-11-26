package com.example.lion_personal.lionopensource.retrofit;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RetrofitApi {

    @POST("path/{path}")
    Single<RetrofitResp> method(@Path("path") String path, @Body RetrofitReq req);
}
