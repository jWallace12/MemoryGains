package a2.csci412.wwu.edu.memorygain;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Jonah on 10/31/2017.
 */

public class LocationRecall extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static FusedLocationProviderApi flpa;
    private static Location location;
    private static final int REQUEST_CODE = 100;
    private static GoogleApiClient gac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_recall);
        flpa = LocationServices.FusedLocationApi;
        gac = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    protected void onStart() {
        super.onStart();
        if (gac != null) {
            gac.connect();
        }
    }

    public void onConnected ( Bundle hint ) {
        //flpa.requestLocationUpdates(gac, locationRequest, this);
        recordLocation();
    }

    public void onConnectionFailed(ConnectionResult result) {
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(this, REQUEST_CODE);
            } catch (IntentSender.SendIntentException ex) {
                Toast.makeText(this, "Problem with the connection, exiting", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void onConnectionSuspended(int num) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            // fixed issue, reconnect
            gac.connect();
        }
    }

    public void recordLocation() {
        try {
            FusedLocationProviderApi flpa = LocationServices.FusedLocationApi;
            location = flpa.getLastLocation(gac);
            long time = System.currentTimeMillis();
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                TextView locView = findViewById(R.id.latLong);
                TextView timeView = findViewById(R.id.time);
                locView.setText("Lat - " + latitude + " === Long - " + longitude);
                //timeView.setText(currentTime);
            }

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

    }



//    public void recordLocation( View v ) {
//        Intent myIntent = new Intent(this, MapsActivity.class);
//        this.startActivity(myIntent);
//    }

    public void goBack( View v ) {
        this.finish( );
    }
}