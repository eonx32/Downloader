package com.example.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RequestActivity extends AppCompatActivity {
    Button baddQ,bdownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        addListenerOnButton1();
        addListenerOnButton2();
    }

    public void addListenerOnButton1() {

        final Context context = this;
        baddQ=(Button) findViewById(R.id.bAddq);

        baddQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Requestqueue.class);
                startActivity(intent);
            }
        });

    }

    public void addListenerOnButton2() {

        final Context context = this;
        bdownload=(Button) findViewById(R.id.bDown);

        bdownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, download.class);
                startActivity(intent);
            }
        });

    }
}
