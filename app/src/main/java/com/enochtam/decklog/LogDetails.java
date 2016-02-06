package com.enochtam.decklog;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LogDetails extends AppCompatActivity {
    EditText longText;
    EditText latText;
    EditText observation;
    EditText speed;
    EditText distance;
    EditText eta;
    EditText remarks;


    DBHelper db;
    int log_id;

    public int year,month,day,hour,minute;
    Button pickDateButton;
    Button pickTimeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Log Details");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        db = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        log_id = intent.getIntExtra("YOLO",0);
        longText = (EditText) findViewById(R.id.longitude);
        latText = (EditText) findViewById(R.id.latitude);
        observation = (EditText) findViewById(R.id.observation);
        speed = (EditText) findViewById(R.id.speed);
        distance = (EditText) findViewById(R.id.distance);
        eta = (EditText) findViewById(R.id.eta);
        remarks = (EditText) findViewById(R.id.remarks);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location !=null){
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    longText.setText(Double.toString(longitude));
                    latText.setText(Double.toString(latitude));
                }
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        pickDateButton = (Button) findViewById(R.id.pickDateButton);
        pickTimeButton = (Button) findViewById(R.id.pickTimeButton);

    }
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_details, menu);
        return true;
    }
    public void saveDetails(View view){

        GregorianCalendar ca = new GregorianCalendar(year, month, day, hour, minute);
        int unix = (int)(ca.getTimeInMillis()/1000);

        DBHelper db = new DBHelper(this);

        db.insertLogItems(log_id,
                unix,
                Float.parseFloat(latText.getText().toString()),
                Float.parseFloat(longText.getText().toString()),
                observation.getText().toString(),
                Float.parseFloat(speed.getText().toString()),
                Float.parseFloat(distance.getText().toString()),
                Float.parseFloat(eta.getText().toString()),remarks.getText().toString());

        finish();
    }

    public void showDatePickerDialog(View v) {
        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                LogDetails.this.year = year;
                LogDetails.this.month = monthOfYear;
                LogDetails.this.day = dayOfMonth;
                pickDateButton.setText(year+"-"+monthOfYear+"-"+dayOfMonth);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void showTimePickerDialog(View v) {
        Calendar c = Calendar.getInstance();

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                LogDetails.this.hour = hourOfDay;
                LogDetails.this.minute = minute;
                pickTimeButton.setText(hourOfDay+":"+minute);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(this)).show();
    }
    public void getCoordinates(View view){
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        if (TextUtils.isEmpty(provider)){
            Toast.makeText(getApplicationContext(),"error getting provider",Toast.LENGTH_SHORT).show();
        }
        PackageManager pm = getApplicationContext().getPackageManager();
        if (pm.checkPermission("android.permission.ACCESS_FINE_LOCATION",getApplicationContext().getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestSingleUpdate(provider, locationListener, null);
            //Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //locationListener.onLocationChanged(l);
        }
        else{
            Toast.makeText(getApplicationContext(),"Unable to obtain Location Permissions",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
