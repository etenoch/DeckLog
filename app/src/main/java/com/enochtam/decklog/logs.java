package com.enochtam.decklog;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

/**
 * Created by jashansudan on 2016-02-06.
 */
public class logs extends AppCompatActivity  {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Logs");

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(logs.this, LogDetails.class);
        startActivity(intent);
    }




}





