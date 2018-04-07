package com.iframe.net.api;

import com.iframe.net.model.IdInfo;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/7/3.
 */
public interface ApiService {
    @GET("param")
    Call<IdInfo> getIdInfo(@Query("id") String id);
}
