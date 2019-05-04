package com.example.Downloader.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Downloader.R;

public class HomeActivity extends AppCompatActivity {
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void request(View view) {
        startActivity(new Intent(this, RequestActivity.class));
    }

    public void history(View view) {
        startActivity(new Intent(this, HistoryActivity.class));
    }
}
