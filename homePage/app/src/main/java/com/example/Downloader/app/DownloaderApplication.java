package com.example.Downloader.app;

import android.app.Application;

import com.example.Downloader.db.DownloaderDBManager;
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
        DownloaderDBManager.getInstance().open();
        LogUtil.info(TAG, "Opening application");
    }

    @Override
    public void onTerminate() {
        LogUtil.info(TAG, "Closing application");
        DownloaderDBManager.getInstance().close();
        super.onTerminate();
    }
}
