package com.enochtam.decklog;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private Pair<ArrayList<String>,int[]> dbLogsData;
    private ArrayList<String> arrayList;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(getApplicationContext());

        dbLogsData = db.getAllLogs();

        arrayList = dbLogsData.first;

        assert getSupportActionBar() != null;
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.main_activity_title));
    }

}
