package com.example.Downloader.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.Downloader.R;
import com.example.Downloader.data.File;
import com.example.Downloader.helper.DownloaderClient;
import com.example.Downloader.helper.DownloaderClientListener;
import com.example.Downloader.util.LogUtil;

import java.util.Objects;


public class RequestActivity extends AppCompatActivity implements DownloaderClientListener {

    private static final String TAG = "RequestActivity";
    private static final String regex =
            "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private EditText addressText, nameText;
    private Spinner partsSpinner;
    private ContentLoadingProgressBar progressBar;
    private DownloaderClient downloaderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        addressText = findViewById(R.id.address);
        nameText = findViewById(R.id.name);
        partsSpinner = findViewById(R.id.parts);
        progressBar = findViewById(R.id.progressbar);
        downloaderClient = DownloaderClient.getInstance();
    }

    public void sendRequest(View view) {

        String address = addressText.getText().toString();
        String name = nameText.getText().toString();
        int parts = Integer.parseInt(partsSpinner.getSelectedItem().toString());

        if(!isValidInput(address, name))
            return ;

        if(progressBar.isShown())
            progressBar.hide();
        else {
            progressBar.show();
            downloaderClient.requestDownload(address, name, parts);
        }
    }

    private boolean isValidInput(String address, String name) {

        if(address.isEmpty() || !address.matches(regex)){
            addressText.setError("Enter a valid file link");
            return false;
        }

        if(name.isEmpty()) {
            nameText.setError("Provide a name for this file");
            return false;
        }

        return true;
    }

    @Override
    public void updateFileInfo(@Nullable File file) {
        progressBar.hide();

        if(Objects.nonNull(file)) {
            finish();
            startActivity(new Intent(this, HistoryActivity.class));
        } else {
            LogUtil.showLongToast("Network Error!!\nPlease try later!!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        downloaderClient.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        downloaderClient.deregister();
    }
}
