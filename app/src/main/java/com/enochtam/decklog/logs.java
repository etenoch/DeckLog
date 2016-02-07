package com.enochtam.decklog;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Logs Activity
 * Created by jashansudan on 2016-02-06.
 */
public class logs extends AppCompatActivity  {

    private ArrayAdapter<String> adapter;
    Button button;
    public String logNumb;
    public int temp;
    public ArrayList<LogItem> AList;
    public ArrayList<String> SList;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);
        assert getSupportActionBar() != null;
        Intent intent = getIntent();
        temp = intent.getIntExtra("YOLO", 0);
        String title = intent.getStringExtra("SWAG");
        getSupportActionBar().setTitle("Log: "+title);
        db = new DBHelper(getApplicationContext());
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(logs.this, LogDetails.class);
        intent.putExtra("SWAG", temp);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AList  = db.getAllLogItems(temp);
        SList = new ArrayList<>();
        for (LogItem i: AList){
            SList.add(i.toString());
        }


        ListView list = (ListView) findViewById(R.id.logListView);
        adapter = new ArrayAdapter<>(this,
                R.layout.custom_simple_list_item_1,
                SList);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), LogDetails.class);
                i.putExtra("YOLO", AList.get(position).id);
                i.putExtra("SWAG", AList.get(position).log_id);
                startActivity(i);
                // Toast.makeText(getApplicationContext(), "my position is " + position + " and my id is " + dbLogsData.get(position).id, Toast.LENGTH_LONG).show();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(AList.get(position).date_time * 1000L);

                String formattedDate = gc.get(Calendar.YEAR) +"-"+ (gc.get(Calendar.MONTH)+1) + "-" + gc.get(Calendar.DAY_OF_MONTH)+  " " + gc.get(Calendar.HOUR_OF_DAY) + ":" +String.format("%02d", gc.get(Calendar.MINUTE));

                AlertDialog deleteConfirm = new AlertDialog.Builder(logs.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this log item from " + formattedDate
                                + " ?")
                        .setIcon(R.drawable.delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.deleteLogsItem(AList.get(position).id);
                                logs.this.onResume();
                                dialog.dismiss();
                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                deleteConfirm.show();
                return true;
            }
        });
    }


}





