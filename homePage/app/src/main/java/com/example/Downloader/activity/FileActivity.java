package com.example.Downloader.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.Downloader.data.File;
import com.example.Downloader.util.LogUtil;
import com.example.Downloader.R;
import com.google.gson.Gson;

public class FileActivity extends AppCompatActivity {

    private static final String TAG = "FileActivity";

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        file = new Gson().fromJson(getIntent().getStringExtra("file"), File.class);
        LogUtil.warn(TAG, file.toString());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(file.getName()+"."+file.getExt());
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
    }
}
