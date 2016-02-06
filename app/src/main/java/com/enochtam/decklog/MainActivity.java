package com.enochtam.decklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent i = new Intent(this,newLogs.class);
        //startActivity(i);

        db = new DBHelper(getApplicationContext());

        //db.insertLogs("Hello","Test","Test2");

        arrayList = db.getAllLogs();

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
                Toast.makeText(getApplicationContext(), "my position is " + position + " and my id is " + id, Toast.LENGTH_LONG).show();
            }
        });

    }
}
