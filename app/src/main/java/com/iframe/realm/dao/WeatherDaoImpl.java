package com.iframe.realm.dao;

/**
 * Created by zsdning on 2016/6/29.
 */

import android.content.Context;

import com.iframe.realm.module.Weather;
import com.iframe.util.RealmUtil;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @className:ProductDaoImpl
 * @desc:
 * @author:zsdning
 * @datetime:16/7/29
 */
public class WeatherDaoImpl implements WeatherDao {

    private Context context;
    private Realm mRealm;

    public WeatherDaoImpl(Context context) {
        mRealm = RealmUtil.getIntance(context).getRealm();
    }


    /**
     * @param weather
     * @throws Exception
     * @同步插入Weather
     */
    @Override
    public void insertWeather(Weather weather) throws Exception {
        mRealm.beginTransaction();
        Weather weather1 = mRealm.copyToRealm(weather);
        mRealm.commitTransaction();
        //mRealm.close();
    }

    /**
     * 获取所有的Weather
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Weather> getAllWeather() throws Exception {
        List<Weather> mlist;
        mlist = mRealm.where(Weather.class).findAll();
        // mRealm.close();
        return mlist;
    }

    /**
     * @param weather
     * @throws Exception
     */
    @Override
    public Weather updateWeather(Weather weather) throws Exception {
        mRealm.beginTransaction();
        Weather weather1 = mRealm.copyToRealmOrUpdate(weather);
        mRealm.commitTransaction();
        // mRealm.close();
        return weather1;
    }

    /**
     * 删除用户
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteWeather(String id) throws Exception {
        //删除 area = ".."的天气
        Weather weather = mRealm.where(Weather.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        weather.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * 查询Weather
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean queryWeather(String id) throws Exception {
        //查询 id = ".."的天气
        RealmResults<Weather> weathers = mRealm.where(Weather.class).equalTo("id", id).findAll();
        mRealm.beginTransaction();
        if(weathers.size() > 0){
            mRealm.commitTransaction();
            return  true;
        }
        mRealm.commitTransaction();
        return false;
    }

    /**
     * 获取Weather
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Weather getWeather(String id) throws Exception {
        //获取 area = ".."的天气
        RealmResults<Weather> weathers = mRealm.where(Weather.class).equalTo("id", id).findAll();
        mRealm.beginTransaction();
        if(weathers.size() > 0){
            mRealm.commitTransaction();
            return  weathers.get(0);
        }
        mRealm.commitTransaction();
        return null;
}

    /**
     * 异步插入Weather
     *
     * @param weather
     * @throws Exception
     */
    @Override
    public void insertWeatherAsync(final Weather weather) throws Exception {
        //一个Realm只能在同一个线程中访问，在子线程中进行数据库操作必须重新获取Realm对象：
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.beginTransaction();
                Weather weather1 = realm.copyToRealm(weather);
                realm.commitTransaction();
                //realm.close();//并且要记得在离开线程时要关闭 realm.close();
            }
        });
        //mRealm.close();//关闭Realm对象
    }

    @Override
    public void finishRealm() throws Exception {
        mRealm.close();
    }
}
