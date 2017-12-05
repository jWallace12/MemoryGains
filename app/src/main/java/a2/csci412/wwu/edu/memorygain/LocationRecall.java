package a2.csci412.wwu.edu.memorygain;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jonah on 10/31/2017.
 */

public class LocationRecall extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static LatLng latLng;
    private LocationManager LM;
    private static String address;
    private String time;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_recall);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // get address, time and date, and write them to the screen
        LM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        latLng = getLocation();
        address = getAddress();
        time = getTime();
        date = getDate();
        TextView addressView = findViewById(R.id.latLong);
        TextView timeView = findViewById(R.id.currTime);
        addressView.setText(address);
        timeView.setText(time);
    }

    // return current GPS coordinates
    public LatLng getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location location = LM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        return null;
    }

    // return current address
    public String getAddress() {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // format and return the address
        String address = addresses.get(0).getAddressLine(0);
        String[] splitter = address.split(",");
        address =  splitter[0] + "," + splitter[1] + ", ";
        splitter = splitter[2].split(" ");
        address += splitter[1];
        return address;
    }

    // get current time
    public String getTime() {
        boolean amOrPm;
        Date currentTime = Calendar.getInstance().getTime();
        String[] splitter = currentTime.toString().split(" ");
        String justTime = splitter[3];
        splitter = justTime.split(":");
        String hour = splitter[0];
        int hourInt = Integer.parseInt(hour);

        // edge case:
        if (hourInt == 0) {
            hourInt = 12;
            amOrPm = true;
        } else {

            // find if the time is AM or PM
            if (hourInt < 12) {
                amOrPm = true;
            } else {
                amOrPm = false;
                hourInt -= 12;
            }
        }

        // format and return time
        String minutes = splitter[1];
        String overallTime = hourInt + ":" + minutes;
        if (amOrPm) {
            overallTime += " AM";
        } else {
            overallTime += " PM";
        }
        return overallTime;
    }

    // return current day of week, month, and date
    public String getDate() {
        Date date = Calendar.getInstance().getTime();
        String dateString = date.toString();
        String[] splitter = dateString.split(" ");
        return splitter[0] + ", " + splitter[1] + " " + splitter[2];
    }



    protected void onStart() {
        super.onStart();
    }

    public void acceptLocation(View v) {

        // retrieve the phrase, and then save it into SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location", address);
        editor.putString("time", time);
        editor.putString("date", date);
        editor.commit();

        // tell main activity that a new timer is being created, and
        // return to main activity
        MainActivity.setLocationBoolean();
        this.finish();
    }

    // get latitude/longitude
    public static LatLng getLatLng() {
        return latLng;
    }

    // get address
    public static String getLocationString() {
        return address;
    }

    public void goToMaps( View v ) {
        Intent myIntent = new Intent(this, MapsActivity.class);
        this.startActivity(myIntent);
    }

    // return to main screen
    public void goBack( View v ) {
        this.finish( );
    }
}