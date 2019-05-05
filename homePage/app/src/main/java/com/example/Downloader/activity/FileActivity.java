package com.example.Downloader.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.Downloader.data.File;
import com.example.Downloader.data.FilePart;
import com.example.Downloader.util.FileUtil;
import com.example.Downloader.util.LogUtil;
import com.example.Downloader.R;
import com.example.Downloader.view.FilePartAdapter;
import com.google.gson.Gson;

import java.util.List;

import static com.example.Downloader.util.Constants.RETRIEVE_URL;

public class FileActivity extends AppCompatActivity implements FilePartAdapter.OnItemClickListener {

    private static final String TAG = "FileActivity";

    private long downloadID;

    private File file;
    private RecyclerView recyclerView;
    private Button mergeButton;

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                populateView();
                LogUtil.showShortToast("Download Complete");
            }
        }
    };

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

        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        recyclerView = findViewById(R.id.cardlist);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mergeButton = findViewById(R.id.merge_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    private void populateView() {
        List<FilePart> fileParts = FileUtil.generateFileParts(file);
        FilePartAdapter filePartAdapter = new FilePartAdapter(fileParts, this);
        recyclerView.setAdapter(filePartAdapter);

        enableMergeButton(fileParts);
    }

    private void enableMergeButton(List<FilePart> fileParts) {
        mergeButton.setVisibility(View.VISIBLE);
        fileParts.forEach(filePart -> {
            if(!filePart.isAvailable())
                mergeButton.setVisibility(View.GONE);
        });
    }

    @Override
    public void onItemClick(int position, FilePart filePart) {
        // Download filePart from server
        LogUtil.info(TAG, filePart.getLink());

        java.io.File file1 = new java.io.File(getExternalFilesDir(null),filePart.getLink()+"."+file.getExt());

        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(RETRIEVE_URL+filePart.getLink()))
                .setTitle(file.getName()+"-"+filePart.getId())// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file1))// Uri of the destination file
                .setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);
    }
}
