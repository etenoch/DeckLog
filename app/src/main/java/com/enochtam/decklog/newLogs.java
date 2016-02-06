package com.enochtam.decklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class newLogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_logs);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("New Log");
    }

    public void buttonOnClick(View v) {
        Intent i = new Intent(this,logs.class);
        startActivity(i);
    }
}
