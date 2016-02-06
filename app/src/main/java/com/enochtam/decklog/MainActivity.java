package com.enochtam.decklog;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Intent i = new Intent(this,newLogs.class);
        startActivity(i);


        assert getSupportActionBar() != null;
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.main_activity_title));
    }
}


