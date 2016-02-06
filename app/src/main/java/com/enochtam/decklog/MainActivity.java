package com.enochtam.decklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        ListView list = (ListView) findViewById(R.id.log_list);
        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                arrayList);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "my position is " + position + " and my id is " + dbLogsData.second[position], Toast.LENGTH_LONG).show();
            }
        });

    }
    public void launchAddLog(View view){
        Intent i = new Intent(this, newLogs.class);
        startActivity(i);
    }
}
