package com.example.Downloader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.Downloader.app.DownloaderApplication;
import com.example.Downloader.util.LogUtil;

public class DownloaderDBManager extends DownloaderDBHelper{

    private final static String TAG = "DownloaderDBManager";

    private static DownloaderDBManager sInstance;
    private DownloaderDBHelper dbHelper;
    private Context mContext;
    private SQLiteDatabase database;

    private DownloaderDBManager(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    public static DownloaderDBManager getInstance () {
        if(sInstance == null)
            sInstance = new DownloaderDBManager(DownloaderApplication.getInstance());
        return sInstance;
    }

    public DownloaderDBManager open() throws SQLException {
        dbHelper = new DownloaderDBHelper(mContext);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String tableName, ContentValues values) {

        LogUtil.info(TAG,"Inserting " + values.toString() + " into " + tableName);
        return database.insert(tableName, null, values);
    }

    public Cursor getAll(String tableName) {
        Cursor res = database.rawQuery( "SELECT * FROM " + tableName, null );
        return res;
    }
}
