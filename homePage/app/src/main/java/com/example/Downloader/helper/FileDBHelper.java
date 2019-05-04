package com.example.Downloader.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.Downloader.DownloaderApplication;
import com.example.Downloader.data.File;
import com.example.Downloader.util.FileUtil;
import com.example.Downloader.util.LogUtil;

import java.util.List;
import java.util.Objects;

public class FileDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SQLiteFile.db";
    private static final int DATABASE_VERSION = 1;
    private static final String FILE_TABLE_NAME = "file";
    public static final String FILE_COLUMN_ID = "_id";
    public static final String FILE_COLUMN_NAME = "name";
    public static final String FILE_COLUMN_EXT = "ext";
    public static final String FILE_COLUMN_PARTS = "parts";

    private static final String TAG = "FileDBHelper";

    private static FileDBHelper mInstance;

    private FileDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized FileDBHelper getInstance() {
        if(Objects.isNull(mInstance))
            mInstance = new FileDBHelper(DownloaderApplication.getInstance());
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.info(TAG,"Creating DB...");
        db.execSQL("CREATE TABLE " + FILE_TABLE_NAME + "(" +
                FILE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                FILE_COLUMN_NAME + " TEXT, " +
                FILE_COLUMN_EXT + " TEXT, " +
                FILE_COLUMN_PARTS + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.info(TAG,"Upgrading DB...");
        db.execSQL("DROP TABLE IF EXISTS " + FILE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertFile(File file) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FILE_COLUMN_ID, file.getId());
        contentValues.put(FILE_COLUMN_NAME, file.getName());
        contentValues.put(FILE_COLUMN_EXT, file.getExt());
        contentValues.put(FILE_COLUMN_PARTS, file.getParts());

        LogUtil.info(TAG,"Inserting " + file + " into DB.");
        db.insert(FILE_TABLE_NAME, null, contentValues);

        return true;
    }

    public List<File> getAllFiles() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + FILE_TABLE_NAME, null );

        List<File> files = FileUtil.getFilesFromCursors(res);
        res.close();

        return files;
    }
}
