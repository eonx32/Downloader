package com.example.Downloader.util;

import android.database.Cursor;

import com.example.Downloader.data.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_EXT;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_ID;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_NAME;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_PARTS;

public class FileUtil {

    private static final String TAG = "FileUtil";
    private static final String regex = "([0-9]+)(\\.)(\\w+)(.*)";

    public static List<File> getFilesFromCursors(Cursor cursor) {

        ArrayList<File> files = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndex(FILE_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(FILE_COLUMN_NAME));
            String ext = cursor.getString(cursor.getColumnIndex(FILE_COLUMN_EXT));
            int parts = cursor.getInt(cursor.getColumnIndex(FILE_COLUMN_PARTS));

            files.add(new File(id, name, ext, parts));
            LogUtil.error(TAG, files.toString());
        }

        return files;
    }

    public static File  getFileFromJSON(String response, String name) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("file");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jsonArray.getString(0));

        if (!matcher.find())
            return null;

        long id = 0;
        if(Objects.nonNull(matcher.group(1)))
            id = Long.parseLong(matcher.group(1));
        String ext = matcher.group(3);
        int parts = jsonArray.length();

        return new File(id, name, ext, parts);
    }

}
