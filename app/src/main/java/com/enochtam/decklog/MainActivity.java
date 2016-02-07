package com.enochtam.decklog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
    private ArrayList<Log> dbLogsData;
    private ArrayList<String> dbLogStrings;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DBHelper(getApplicationContext());
        dbLogsData = db.getLogObjects();
        dbLogStrings = new ArrayList<>();
        for (Log i : dbLogsData) {
            dbLogStrings.add(i.toString());
        }

        assert getSupportActionBar() != null;
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.main_activity_title));

        ListView list = (ListView) findViewById(R.id.log_list);
        adapter = new ArrayAdapter<>(this, R.layout.custom_simple_list_item_1, dbLogStrings);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), logs.class);
                i.putExtra("YOLO", dbLogsData.get(position).id);
                i.putExtra("SWAG", dbLogsData.get(position).name);
                startActivity(i);
                // Toast.makeText(getApplicationContext(), "my position is " + position + " and my id is " + dbLogsData.get(position).id, Toast.LENGTH_LONG).show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog deleteConfirm = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete " + dbLogsData.get(position).name
                                + " and all associated log entries?")
                        .setIcon(R.drawable.delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.deleteLog(dbLogsData.get(position).id);
                                MainActivity.this.onResume();
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

    public void launchAddLog(View view) {
        Intent i = new Intent(getApplicationContext(), newLogs.class);
        startActivity(i);
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
