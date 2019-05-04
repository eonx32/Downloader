package com.example.Downloader.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.Downloader.data.File;
import com.example.Downloader.util.LogUtil;
import com.example.Downloader.R;

import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_EXT;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_ID;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_NAME;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_PARTS;

public class FileActivity extends AppCompatActivity {

    private static final String TAG = "FileActivity";

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        Intent intent = getIntent();

        file = new File(
                intent.getLongExtra(FILE_COLUMN_ID, 0),
                intent.getStringExtra(FILE_COLUMN_NAME),
                intent.getStringExtra(FILE_COLUMN_EXT),
                intent.getIntExtra(FILE_COLUMN_PARTS, 0)
        );

        LogUtil.error(TAG, file.toString());
    }
}
