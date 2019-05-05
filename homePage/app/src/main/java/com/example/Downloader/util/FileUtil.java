package com.example.Downloader.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.example.Downloader.R;
import com.example.Downloader.app.DownloaderApplication;
import com.example.Downloader.data.File;
import com.example.Downloader.data.FilePart;
import com.example.Downloader.data.Response;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
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
                    file.getExt(),
                    FILE_SUFFIX[i]
            );
            FilePart filePart = new FilePart(address, i+1, searchFileInStorage(address+"."+file.getExt()));
            fileParts.add(filePart);
            LogUtil.warn(TAG, filePart.toString());
        }

        String address = String.join(
                SEPARATOR,
                file.getName(),
                file.getExt()
        );
        FilePart filePart = new FilePart(address, 0, searchFileInStorage(address));

        if(filePart.isAvailable()) {
            fileParts = new ArrayList<>();
            fileParts.add(filePart);
        }

        return fileParts;
    }

    private static boolean searchFileInStorage(String address) {

        java.io.File file = new java.io.File(
                getDownloaderDirectory(),
                address);
        return file.exists();
    }

    public static void mergeFiles(Context context, File file) {

        List<FilePart> fileParts = generateFileParts(file);

        String address = String.join(
                SEPARATOR,
                file.getName(),
                file.getExt()
        );
        java.io.File fileOutput = new java.io.File(
                getDownloaderDirectory(),
                address);

        if(fileOutput.exists())
            fileOutput.delete();

        try {
            fileOutput.createNewFile();
        } catch (IOException e) {
            LogUtil.error(TAG, e.getMessage());
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(fileOutput.getAbsolutePath());

            byte bytes[] = new byte[256];
            for(int i=0; i<fileParts.size(); i++) {
                FilePart filePart = fileParts.get(i);
                java.io.File fileInput = new java.io.File(
                    getDownloaderDirectory(),
                    filePart.getLink()+"."+file.getExt());

                if(!fileInput.exists())
                    throw new IOException("Error in file Reading "+filePart.toString());

                FileInputStream inputStream = new FileInputStream(fileInput.getPath());
                while(inputStream.read(bytes)!=-1)
                    outputStream.write(bytes);
                inputStream.close();
            }

            outputStream.close();
        } catch (IOException e) {
            LogUtil.error(TAG, "File write failed: " + e.toString());
        }
    }

    public static void openFile(java.io.File url) {

        try {

            Context context = DownloaderApplication.getInstance().getApplicationContext();
            Uri uri = FileProvider.getUriForFile(context,
                    "com.example.Downloader.fileprovider",
                    url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            DownloaderApplication.getInstance().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            LogUtil.showShortToast("No application found which can open the file");
        }
    }

    public static String getDownloaderDirectory() {
        java.io.File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsoluteFile();
        if(!file.exists())
            file.mkdirs();
        return file.getAbsolutePath();
    }
}
