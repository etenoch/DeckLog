package com.enochtam.decklog;

import android.view.Menu;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;;
import android.view.View;

/**
 * Created by jashansudan on 2016-02-06.
 */
public class logs extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Logs");
    }





}
