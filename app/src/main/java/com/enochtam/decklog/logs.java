package com.enochtam.decklog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;

/**
 * Created by jashansudan on 2016-02-06.
 */
public class logs extends AppCompatActivity  {

    private Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Logs");
        btnClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });
    }



}
