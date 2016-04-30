package com.example.androidstudioessentialtraining;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i< 5 ; i++){
            builder.append(getString(R.string.lorem_ipsum) + "\n\n");
        }
        TextView tv = (TextView) findViewById(R.id.longText);
        tv.setText(builder.toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
