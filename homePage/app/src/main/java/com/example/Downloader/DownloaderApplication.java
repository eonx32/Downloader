package com.example.Downloader;

import android.app.Application;

import com.example.Downloader.helper.FileDBHelper;
import com.example.Downloader.util.LogUtil;

public class DownloaderApplication extends Application {

    private static final String TAG = "DownloaderApplication";

    private static DownloaderApplication mInstance;

    public static synchronized DownloaderApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        LogUtil.info(TAG, "Opening application");
    }

    @Override
    public void onTerminate() {
        LogUtil.info(TAG, "Closing application");
        FileDBHelper.getInstance().close();
        super.onTerminate();
    }
}
