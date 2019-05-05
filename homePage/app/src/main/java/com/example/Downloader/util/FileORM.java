package com.example.Downloader.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.Downloader.data.File;
import com.example.Downloader.data.Response;
import com.example.Downloader.db.DownloaderDBManager;

import java.util.ArrayList;
import java.util.List;

public class FileORM {

    private static final String TAG = "FileORM";

    private static final String TABLE_NAME = "file";

    private static final String COMMA_SEP = ", ";

    private static final String COLUMN_FILE_ID_TYPE = "INTEGER PRIMARY KEY";
    private static final String COLUMN_FILE_ID = "fileid";

    private static final String COLUMN_NAME_TYPE = "TEXT";
    private static final String COLUMN_NAME = "name";

    private static final String COLUMN_EXT_TYPE = "TEXT";
    private static final String COLUMN_EXT = "ext";

    private static final String COLUMN_PARTS_TYPE = "INTEGER";
    private static final String COLUMN_PARTS = "parts";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_FILE_ID + " " + COLUMN_FILE_ID_TYPE + COMMA_SEP +
                    COLUMN_NAME + " " + COLUMN_NAME_TYPE + COMMA_SEP +
                    COLUMN_EXT + " " + COLUMN_EXT_TYPE + COMMA_SEP +
                    COLUMN_PARTS + " " + COLUMN_PARTS_TYPE +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static void saveFile(File file) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FILE_ID, file.getId());
        contentValues.put(COLUMN_NAME, file.getName());
        contentValues.put(COLUMN_EXT, file.getExt());
        contentValues.put(COLUMN_PARTS, file.getParts());

        long fileID = DownloaderDBManager.getInstance().insert(TABLE_NAME, contentValues);
        LogUtil.info(TAG, "Inserted new Page with ID: " + fileID);
    }

    public static List<File> getAllFiles() {

        Cursor cursor = DownloaderDBManager.getInstance().getAll(TABLE_NAME);
        List<File> files = getFilesFromCursors(cursor);

        return files;
    }

    public static List<File> getFilesFromCursors(Cursor cursor) {

        ArrayList<File> files = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_FILE_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String ext = cursor.getString(cursor.getColumnIndex(COLUMN_EXT));
            int parts = cursor.getInt(cursor.getColumnIndex(COLUMN_PARTS));

            files.add(new File(id, name, ext, parts));
            LogUtil.error(TAG, files.toString());
        }

        return files;
    }
}
