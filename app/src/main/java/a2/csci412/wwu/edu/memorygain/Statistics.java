package a2.csci412.wwu.edu.memorygain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Jonah on 10/31/2017.
 */

public class Statistics extends AppCompatActivity{

    DatabaseManager dbManager;          // database instance
    private static DecimalFormat df2;   // double formatter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        df2 = new DecimalFormat(".##");
        setContentView(R.layout.activity_statistics);
        updateView();
    }

    // update statistics
    public void updateView( ) {

        // find all views by their ids
        TextView phraseText = ( TextView ) findViewById(R.id.phraseStats);
        TextView locationText = ( TextView ) findViewById(R.id.locationStats);
        TextView imageText = ( TextView ) findViewById(R.id.imageStats);
        TextView overallText = ( TextView ) findViewById(R.id.overallStats);

        // retrieve all the recalls in the database
        ArrayList<Recall> phraseRecalls = dbManager.selectByType("phrase");
        ArrayList<Recall> locationRecalls = dbManager.selectByType("location");
        ArrayList<Recall> imageRecalls = dbManager.selectByType("image");

        double phraseSuccess = 0;       // phrase recall successes
        double locationSuccess = 0;     // location recall successes
        double imageSuccess = 0;        // image recall successes
        double overallSuccess = 0;      // overall recall successes


        // track successes for all recalls
        for (Recall recall : phraseRecalls) {
            if (recall.getPass().equals("pass")) {
                phraseSuccess++;
                overallSuccess++;
            }
        }

        for (Recall recall : locationRecalls) {
            if (recall.getPass().equals("pass")) {
                locationSuccess++;
                overallSuccess++;
            }
        }

        for (Recall recall : imageRecalls) {
            if (recall.getPass().equals("pass")) {
                imageSuccess++;
                overallSuccess++;
            }
        }


        // calculate the percentages, and write them to their TextViews
        double phrasePercentage = (phraseSuccess / phraseRecalls.size());
        double locationPercentage = (locationSuccess / locationRecalls.size());
        double imagePercentage = (imageSuccess / locationRecalls.size());
        double allRecalls = locationRecalls.size() + phraseRecalls.size();

        if (Double.isNaN(phrasePercentage)) {
            phraseText.setText("No entries");
        } else {
            phraseText.setText((phrasePercentage * 100) + "%");
        }

        if (Double.isNaN(locationPercentage)) {
            locationText.setText("No entries");
        } else {
            locationText.setText((locationPercentage * 100) + "%");
        }


        if (Double.isNaN(imagePercentage)) {
            imageText.setText("No entries");
        } else {
            imageText.setText((imagePercentage * 100) + "%");
        }

        double overallStats = (overallSuccess / allRecalls) * 100;
        if (Double.isNaN(overallStats)) {
            overallText.setText("No entries");
        } else {
            overallText.setText(df2.format(overallStats) + "%");
        }
    }

    // return to main screen
    public void goBack( View v ) {
        this.finish( );
    }
}
