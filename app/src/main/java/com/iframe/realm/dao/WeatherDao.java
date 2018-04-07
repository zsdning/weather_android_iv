package com.iframe.realm.dao;

import com.iframe.realm.module.Weather;

import java.util.List;

/**
 * Created by zsdning on 2016/7/16.
 */
public interface WeatherDao{

    /**
     * 插入Weather
     * @param weather
     * @throws Exception
     */
    void insertWeather(Weather weather)throws Exception;


    /**
     * 获取所有的Weather
     * @return
     * @throws Exception
     */
    List<Weather> getAllWeather()throws Exception;

    /**
     *  更新Weather
     *  @param weather
     *  @return
     * @throws Exception
     */
    Weather updateWeather(Weather weather)throws Exception;

    /**
     * 删除Weather
     * @param id
     * @throws Exception
     */
    void deleteWeather(String id)throws Exception;

    /**
     * 查询Weather
     * @param id
     * @return
     * @throws Exception
     */
    boolean queryWeather(String id)throws Exception;

    /**
     * 获取Weather
     * @param id
     * @return
     * @throws Exception
     */
    Weather getWeather(String id)throws Exception;


    /**
     * 异步插入Weather
     * @param weather
     * @throws Exception
     */
    void insertWeatherAsync(Weather weather)throws Exception;

    /**
     * 结束  释放数据库资源
     * @throws Exception
     */
    void finishRealm()throws  Exception;
}
