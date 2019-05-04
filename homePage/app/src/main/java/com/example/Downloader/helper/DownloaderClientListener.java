package com.example.Downloader.helper;


import android.support.annotation.Nullable;

import com.example.Downloader.data.File;

public interface DownloaderClientListener {

    public void updateFileInfo(@Nullable File file);

}


