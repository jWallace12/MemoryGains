package a2.csci412.wwu.edu.memorygain;

import android.Manifest;
import android.content.Context;
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
    private LatLng latLng;
    private LocationManager LM;
    private String address;
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_recall);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        LM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        latLng = getLocation();
        address = getAddress();
        time = getTime();
        TextView  addressView = findViewById(R.id.latLong);
        TextView timeView = findViewById(R.id.currTime);
        addressView.setText(address);
        timeView.setText(time);
    }

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

    public String getAddress() {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {

            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return addresses.get(0).getAddressLine(0);
    }

    public String getTime() {
        Date currentTime = Calendar.getInstance().getTime();
        String[] splitter = currentTime.toString().split(" ");
        String justTime = splitter[3];
        splitter = justTime.split(":");
        String hour = splitter[0];
        int hourInt = Integer.parseInt(hour);
        boolean amOrPm;
        if (hourInt < 13) {
            amOrPm = true;
        } else {
            amOrPm = false;
            hourInt -= 12;
        }
        String minutes = splitter[1];
        String overallTime = hourInt + ":" + minutes;
        if (amOrPm) {
            overallTime += " AM";
        } else {
            overallTime += " PM";
        }
        return overallTime;
    }



    protected void onStart() {
        super.onStart();
    }

    public void acceptLocation(View v) {
        // retrieve the phrase, and then save it into SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location", address);
        editor.putString("time", time);
        editor.commit();

        // tell main activity that a new timer is being created, and
        // return to main activity
        MainActivity.setLocationBoolean();
        this.finish();
    }


    public void goBack( View v ) {
        this.finish( );
    }
}