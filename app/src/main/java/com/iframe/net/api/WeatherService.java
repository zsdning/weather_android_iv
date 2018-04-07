package com.iframe.net.api;

import com.iframe.net.model.CityInfo;
import com.iframe.net.model.WeatherInfo;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/7/9.
 */
public interface WeatherService {
    /**
     * 城市查询
     */
    @GET("city")
    Call<CityInfo> getCity(@Query("appkey") String appkey);

    /**
     * 获取天气
     */
    @GET("query")
    Call<WeatherInfo> getWeather(@Query("appkey") String appkey, @Query("city") String city);
}
