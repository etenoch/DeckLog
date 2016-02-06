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
    DBHelper db;
    int log_id;

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
        EditText dT = (EditText) findViewById(R.id.dateTime);

        //db.insertLogItems(log_id,)
    }
    /*
    public void getTimeDate(View view){
        GregorianCalendar currCal = new GregorianCalendar();
        int day = currCal.get(Calendar.DAY_OF_MONTH);
        int month = currCal.get(Calendar.MONTH) + 1;
        int year = currCal.get(Calendar.YEAR);
        int hour = currCal.get(Calendar.HOUR_OF_DAY);
        int minute = currCal.get(Calendar.MINUTE);
        String timeDate = year + "/" + month + "/" + day + "-" + hour +":" + minute;
        EditText tDate = (EditText) findViewById(R.id.dateTime);
        tDate.setText(timeDate);
    }
    */
    public void showDatePickerDialog(View v) {
        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getApplicationContext(),"Time: "+year+monthOfYear+dayOfMonth,Toast.LENGTH_LONG);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void showTimePickerDialog(View v) {
        Calendar c = Calendar.getInstance();

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(),"Time: "+hourOfDay+minute,Toast.LENGTH_LONG);
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
