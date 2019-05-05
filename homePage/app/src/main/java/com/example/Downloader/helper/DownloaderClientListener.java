package com.example.Downloader.helper;


import android.support.annotation.Nullable;

import com.example.Downloader.data.File;
import com.example.Downloader.data.Response;

public interface DownloaderClientListener {

    public void updateFileInfo(@Nullable Response response);

}


