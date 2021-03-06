package com.example.Downloader.util;

import android.util.Log;
import android.widget.Toast;

import com.example.Downloader.app.DownloaderApplication;
import com.example.Downloader.data.FilePart;

public class LogUtil {

    public static void info(String TAG, String message) {
        Log.i(TAG, message);
    }

    public static void error(String TAG, String message) {
        Log.e(TAG, message);
    }

    public static void warn(String TAG, String message) {
        Log.w(TAG, message);
    }

    public static void showShortToast(String message) {
        Toast.makeText(DownloaderApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String message) {
        Toast.makeText(DownloaderApplication.getInstance(), message, Toast.LENGTH_LONG).show();
    }
}
