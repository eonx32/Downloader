package com.example.Downloader.util;

import com.example.Downloader.app.DownloaderApplication;
import com.example.Downloader.data.File;
import com.example.Downloader.data.FilePart;
import com.example.Downloader.data.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.Downloader.util.Constants.FILE_SUFFIX;

public class FileUtil {

    private static final String TAG = "FileUtil";
    private static final String regex = "([0-9]+)(\\.)(\\w+)(.*)";
    private static final String SEPARATOR = ".";

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

    public static List<FilePart> generateFileParts(File file) {
        ArrayList<FilePart> fileParts = new ArrayList<>();

        for(int i=0; i<file.getParts(); i++) {

            String address = String.join(
                    SEPARATOR,
                    String.valueOf(file.getId()),
                    file.getName(),
                    FILE_SUFFIX[i]
            );
            FilePart filePart = new FilePart(address, i+1, searchFileInStorage(address));
            fileParts.add(filePart);
            LogUtil.warn(TAG, filePart.toString());
        }

        return fileParts;
    }

    private static boolean searchFileInStorage(String address) {

        java.io.File file = new java.io.File(DownloaderApplication.getInstance().getFilesDir(),
                address);
        return file.exists();
    }
}
