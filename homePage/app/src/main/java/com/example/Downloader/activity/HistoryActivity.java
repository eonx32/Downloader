package com.example.Downloader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.Downloader.data.File;
import com.example.Downloader.data.FileAdapter;
import com.example.Downloader.helper.FileDBHelper;
import com.example.Downloader.R;

import java.util.List;

import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_EXT;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_ID;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_NAME;
import static com.example.Downloader.helper.FileDBHelper.FILE_COLUMN_PARTS;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listview);
    }

    @Override
    public void onStart() {
        super.onStart();
        populateView();
    }

    private void populateView() {
        List<File> files = FileDBHelper.getInstance().getAllFiles();
        FileAdapter adapter = new FileAdapter(this, R.layout.file_item_layout, files);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File file = (File) parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), FileActivity.class);
                intent.putExtra(FILE_COLUMN_ID, file.getId());
                intent.putExtra(FILE_COLUMN_NAME, file.getName());
                intent.putExtra(FILE_COLUMN_EXT, file.getExt());
                intent.putExtra(FILE_COLUMN_PARTS, file.getParts());
                startActivity(intent);
            }
        });
    }
}
