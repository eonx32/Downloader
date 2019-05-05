package com.example.Downloader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.Downloader.data.File;
import com.example.Downloader.data.FileAdapter;
import com.example.Downloader.R;
import com.example.Downloader.util.FileORM;
import com.google.gson.Gson;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements FileAdapter.OnItemClickListener{

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        populateView();
    }

    private void populateView() {
        List<File> files = FileORM.getAllFiles();
        FileAdapter adapter = new FileAdapter(files, this);
        list.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, File file) {
        Intent intent = new Intent(getApplicationContext(), FileActivity.class);
        intent.putExtra("file", new Gson().toJson(file));
        startActivity(intent);
    }
}
