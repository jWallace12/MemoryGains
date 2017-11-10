package a2.csci412.wwu.edu.memorygain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonah on 10/31/2017.
 */

public class Statistics extends AppCompatActivity{

    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_statistics);

        updateView();
    }

    // update statistics
    public void updateView( ) {

        // find all views by their ids
        TextView locationText = ( TextView ) findViewById(R.id.locationStats);
        TextView phraseText = ( TextView ) findViewById(R.id.phraseStats);
        TextView overallText = ( TextView ) findViewById(R.id.overallStats);

        // retrieve all the recalls in the database
        ArrayList<Recall> locationRecalls = dbManager.selectByType("location");
        ArrayList<Recall> phraseRecalls = dbManager.selectByType("phrase");

        double locationSuccess = 0;     // location recall successes
        double phraseSuccess = 0;       // phrase recall successes
        double overallSuccess = 0;      // overall recall successes


        // track successes for all recalls

        for (Recall recall : locationRecalls) {
            if (recall.getPass().equals("1")) {
                locationSuccess++;
                overallSuccess++;
            }
        }

        for (Recall recall : phraseRecalls) {
            if (recall.getPass().equals("1")) {
                phraseSuccess++;
                overallSuccess++;
            }
        }

        // calculate the percentages, and write them to their TextViews
        double locationPercentage = (locationSuccess / locationRecalls.size());
        double phrasePercentage = (phraseSuccess / phraseRecalls.size());
        double allRecalls = locationRecalls.size() + phraseRecalls.size();

        locationText.setText("%" + (locationPercentage * 100));
        phraseText.setText("%" + (phrasePercentage * 100));
        overallText.setText("%" + (overallSuccess / allRecalls));
    }

    public void goBack( View v ) {
        this.finish( );
    }
}
