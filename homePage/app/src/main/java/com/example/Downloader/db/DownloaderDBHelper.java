package com.example.Downloader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.Downloader.util.FileORM;
import com.example.Downloader.util.LogUtil;

public class DownloaderDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SQLiteFile.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "DownloaderDBHelper";

    protected DownloaderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.info(TAG,"Creating DB...");
        db.execSQL(FileORM.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.info(TAG,"Upgrading DB...");
        db.execSQL(FileORM.SQL_DROP_TABLE);
        onCreate(db);
    }
}
