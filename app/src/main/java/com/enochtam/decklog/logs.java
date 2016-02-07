package com.enochtam.decklog;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import java.util.ArrayList;

/**
 * Created by jashansudan on 2016-02-06.
 */
public class logs extends AppCompatActivity  {

    private ArrayAdapter<String> adapter;
    Button button;
    public String logNumb;
    public int temp;
    public ArrayList<LogItem> AList;
    public ArrayList<String> SList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);
        assert getSupportActionBar() != null;
        Intent intent = getIntent();
        temp = intent.getIntExtra("YOLO", 0);
        String title = intent.getStringExtra("SWAG");
        getSupportActionBar().setTitle("Log: "+title);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(logs.this, LogDetails.class);
        intent.putExtra("YOLO", temp);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper newDb = new DBHelper(getApplicationContext());
        AList  = newDb.getAllLogItems(temp);
        SList = new ArrayList<String>();
        for (LogItem i: AList){
            SList.add(i.toString());
        }

        ListView list = (ListView) findViewById(R.id.log_list);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                SList);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), logs.class);
                i.putExtra("YOLO", AList.get(position).id);
                startActivity(i);
                // Toast.makeText(getApplicationContext(), "my position is " + position + " and my id is " + dbLogsData.get(position).id, Toast.LENGTH_LONG).show();
            }
        });
    }









}





