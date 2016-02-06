package com.enochtam.decklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class newLogs extends AppCompatActivity {
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_logs);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("New Log");
    }

    public void buttonOnClick(View v) {
        db = new DBHelper(getApplicationContext());
        EditText logNameText = (EditText) findViewById(R.id.logName);
        EditText vesselText = (EditText) findViewById(R.id.vessel);
        EditText navigatorText = (EditText) findViewById(R.id.navigator);
        db.insertLogs(logNameText.getText().toString(),vesselText.getText().toString(),navigatorText.getText().toString());
        Intent i = new Intent(this,logs.class);
        startActivity(i);
    }
}
