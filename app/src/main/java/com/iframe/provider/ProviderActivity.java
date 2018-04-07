package com.iframe.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.iframe.R;
import com.iframe.base.BaseActivity;

public class ProviderActivity extends BaseActivity {
    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        Uri recentcityUri = Uri.parse("content://com.iframe.provider.recentCityProvider/recentcity");

        //删除数据
        getContentResolver().delete(recentcityUri,null,null);

        //插入数据
        ContentValues values = new ContentValues();
        values.put("id",1);
        values.put("name","苏州");
        values.put("date",2017);
        getContentResolver().insert(recentcityUri,values);

        //更新数据
        values.put("id",1);
        values.put("name","上海");
        values.put("date",2017);
        getContentResolver().update(recentcityUri,values,null,null);

        //查询数据
        Cursor recentcityCursor = getContentResolver().query(recentcityUri,new String[]{"id","name","date"},null,null,null);
        while (recentcityCursor.moveToNext()){
            String cityName = recentcityCursor.getString(1);
            Log.i(TAG, "query recentcity: " + cityName);

        }
        recentcityCursor.close();
    }
}
