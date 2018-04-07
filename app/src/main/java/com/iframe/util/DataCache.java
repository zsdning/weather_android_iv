package com.iframe.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataCache {
	private static final String dbName = "cache.db";
	private static final String TABLE = "cache";
	private static final String ID = "id";
	private static final String URL = "url";
	private static final String DATA = "data";
	private static final String TIMESTAMP = "timestamp";
	private static final String INPUT_BYTES = "input_bytes";
	private static SQLiteDatabase database;
	private File dbFile;
	private static DataCache dataCache;
	private Context context;

	public DataCache(Context context) {
		if (init(context))
			initTable();
	}

	public static DataCache newInstance(Context context) {
		if (dataCache == null) {
			dataCache = new DataCache(context);
		}
		return dataCache;
	}

	public static DataCache getDataCache() {
		return dataCache;
	}

	private boolean init(Context context) {
		try {
			this.dbFile = new File(context.getCacheDir() + File.separator + "cache.db");
			database = SQLiteDatabase.openOrCreateDatabase(this.dbFile, null);
			Log.d("create db", this.dbFile.getAbsolutePath());
			return this.dbFile != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void initTable() {
		if (database != null)
			try {
				database.execSQL(
						"CREATE TABLE IF NOT EXISTS cache (id  INTEGER PRIMARY KEY AUTOINCREMENT, url  TEXT UNIQUE NOT NULL, data TEXT UNIQUE NOT NULL,timestamp  INTEGER NOT NULL DEFAULT 0  );");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public synchronized void saveToCache(String url, String jsontext) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
		String date = sDateFormat.format(new Date());
		if (database == null) {
			return;
		}
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("url", url);
			contentValues.put("data", jsontext);
			contentValues.put("timestamp", date);

			long id = database.insertWithOnConflict("cache", "data", contentValues, 5);
			Log.e("id is a ", id + "");
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public synchronized String queryCache(String url) {
		if (database == null) {
			return null;
		}

		try {
			Cursor cursor = database.rawQuery("SELECT * FROM Cache WHERE url == ?", new String[] { url });
			if (cursor.moveToNext()) {
				return cursor.getString(cursor.getColumnIndex("data"));
			}
			Log.d("query db", cursor.getString(cursor.getColumnIndex("data")));
		} catch (Exception localException) {
			localException.printStackTrace();
		}

		return null;
	}

	public void clearCache() {
		if (database != null)
			try {
				database.delete("cache", null, null);
				database.setTransactionSuccessful();
			} catch (Exception localException) {
				localException.printStackTrace();
			} finally {
				database.endTransaction();
			}
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (database != null)
			try {
				database.close();
			} catch (Exception localException) {
				localException.printStackTrace();
			}
	}
}