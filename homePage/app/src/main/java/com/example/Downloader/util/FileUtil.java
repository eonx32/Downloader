package com.example.Downloader.util;

import com.example.Downloader.data.File;
import com.example.Downloader.data.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    private static final String TAG = "FileUtil";
    private static final String regex = "([0-9]+)(\\.)(\\w+)(.*)";

    public static File getFileFromResponse(Response response, String name) {

        String filePart = response.getFiles().get(0);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filePart);

        if (!matcher.find())
            return null;

        long id = 0;
        if(Objects.nonNull(matcher.group(1)))
            id = Long.parseLong(matcher.group(1));
        String ext = matcher.group(3);
        int parts = response.getFiles().size();

        return new File(id, name, ext, parts);
    }

}
