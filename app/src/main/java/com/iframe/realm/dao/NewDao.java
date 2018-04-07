package com.iframe.realm.dao;

import android.content.Context;

import com.iframe.realm.module.Weather;

/**
 * 测试  使用泛型
 * Created by zsdning on 2016/7/29.
 */
public class NewDao extends RealmDaoImpl<Weather> {
    public NewDao(){
        super();
    }

    public NewDao(Context context){
        super(context);
    }
}
