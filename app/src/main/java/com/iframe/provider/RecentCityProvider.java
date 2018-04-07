package com.iframe.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iframe.database.DatabaseHelper;

/**
 * Created by Administrator on 2017/6/3.
 */

public class RecentCityProvider extends ContentProvider{
    private static final String TAG = "RecentCityProvider";
    private Context mContext;
    private SQLiteDatabase mDb;

    //AUTHORITY是ContentProvider的唯一标识，建议在命名时加上包名前缀
    public static final String AUTHORITY = "com.iframe.provider.recentCityProvider";

    public static final Uri RECENTCITY_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recentcity");
    public static final int RECENTCITY_URI_CODE = 0;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(AUTHORITY,"recentcity",RECENTCITY_URI_CODE);
    }


    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreate, current thread: " + Thread.currentThread().getName());
        mContext = getContext();
        //ContentProvider创建时，初始化数据库。需要在子线程中执行耗时的数据库操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                initProviderData();
            }
        }).start();
        return true;
    }

    private void initProviderData(){
        mDb = new DatabaseHelper(mContext).getWritableDatabase();
        //初始化数据库
        // TODO: 2017/6/4  
    }

    private String getTableName(Uri uri){
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case RECENTCITY_URI_CODE:
                tableName = "recentcity";
                break;
            default:
                break;
        }
        return tableName;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG,"query, current thread: " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG,"getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG,"insert");
        String table = getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table,null,values);
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG,"insert");
        String table = getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table,selection,selectionArgs);
        if(count > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG,"update");
        String table = getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table,values,selection,selectionArgs);
        if(row > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return row;
    }
}
