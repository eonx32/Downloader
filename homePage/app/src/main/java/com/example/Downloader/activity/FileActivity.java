package com.example.Downloader.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.Downloader.data.File;
import com.example.Downloader.data.FilePart;
import com.example.Downloader.util.FileUtil;
import com.example.Downloader.util.LogUtil;
import com.example.Downloader.R;
import com.example.Downloader.view.FilePartAdapter;
import com.google.gson.Gson;

import java.util.List;

public class FileActivity extends AppCompatActivity implements FilePartAdapter.OnItemClickListener {

    private static final String TAG = "FileActivity";

    private File file;
    private RecyclerView recyclerView;

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

        recyclerView = findViewById(R.id.cardlist);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateView();
    }

    private void populateView() {
        List<FilePart> fileParts = FileUtil.generateFileParts(file);
        FilePartAdapter filePartAdapter = new FilePartAdapter(fileParts, this);
        recyclerView.setAdapter(filePartAdapter);
    }

    @Override
    public void onItemClick(int position, FilePart filePart) {
        // Download filePart from server here
        LogUtil.showLongToast(filePart.getLink());
    }
}
