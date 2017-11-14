package a2.csci412.wwu.edu.memorygain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Jonah on 10/31/2017.
 */

public class LocationRecall extends AppCompatActivity{

    private static String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_recall);
    }

    public void recordLocation( View v ) {
        Intent myIntent = new Intent(this, MapsActivity.class);
        this.startActivity(myIntent);
    }

    public void goBack( View v ) {
        this.finish( );
    }
}