package a2.csci412.wwu.edu.memorygain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Jonah on 10/31/2017.
 */

public class Statistics extends AppCompatActivity{

    DatabaseManager dbManager;          // database instance
    private static DecimalFormat df2;   // double formatter


    //Graph stuff
    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DatabaseManager(this);
        df2 = new DecimalFormat(".##");
        setContentView(R.layout.activity_statistics);

        chart = (BarChart) findViewById(R.id.chart1);
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<String>();
        Bardataset = new BarDataSet(BARENTRY, "Phrase, Location, Image, Overall");
        BARDATA = new BarData(BarEntryLabels, Bardataset);

        //Bar graph
        updateView();
    }

    // update statistics
    public void updateView() {

        // find all views by their ids
        TextView phraseText = ( TextView ) findViewById(R.id.phraseStats);
        TextView locationText = ( TextView ) findViewById(R.id.locationStats);
        TextView imageText = ( TextView ) findViewById(R.id.imageStats);
        TextView overallText = ( TextView ) findViewById(R.id.overallStats);

        // retrieve all the recalls in the database
        ArrayList<Recall> phraseRecalls = dbManager.selectByType("phrase");
        ArrayList<Recall> locationRecalls = dbManager.selectByType("location");
        ArrayList<Recall> imageRecalls = dbManager.selectByType("image");

        float phraseSuccess = 0;       // phrase recall successes
        float locationSuccess = 0;     // location recall successes
        float imageSuccess = 0;        // image recall successes
        float overallSuccess = 0;      // overall recall successes


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
        double imagePercentage = (imageSuccess / imageRecalls.size());
        double allRecalls = locationRecalls.size() + phraseRecalls.size() + imageRecalls.size();
        double overallStats = (overallSuccess / allRecalls);


        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(BARDATA);
        chart.animateY(3000);


        if (Double.isNaN(phrasePercentage)) {
            phraseText.setText("No entries");
            BARENTRY.add(new BarEntry(0, 0));
            BarEntryLabels.add("");
        } else {
            phraseText.setText(df2.format(phrasePercentage * 100) + "%");
            BARENTRY.add(new BarEntry((float) phrasePercentage, 0));
            BarEntryLabels.add("Phrase");
        }

        if (Double.isNaN(locationPercentage)) {
            locationText.setText("No entries");
            BARENTRY.add(new BarEntry(0, 1));
            BarEntryLabels.add("");
        } else {
            locationText.setText(df2.format(locationPercentage * 100) + "%");
            BARENTRY.add(new BarEntry((float) locationPercentage, 1));
            BarEntryLabels.add("Location");
        }


        if (Double.isNaN(imagePercentage)) {
            imageText.setText("No entries");
            BARENTRY.add(new BarEntry(0, 2));
            BarEntryLabels.add("");
        } else {
            imageText.setText(df2.format(imagePercentage * 100) + "%");
            BARENTRY.add(new BarEntry((float) imagePercentage, 2));
            BarEntryLabels.add("Image");
        }

        if (Double.isNaN(overallStats)) {
            overallText.setText("No entries");
            BARENTRY.add(new BarEntry(0, 3));
            BarEntryLabels.add("");
        } else {
            overallText.setText(df2.format(overallStats * 100) + "%");
            BARENTRY.add(new BarEntry((float) overallStats, 3));
            BarEntryLabels.add("Overall");
        }
    }

    // return to main screen
    public void goBack( View v ) {
        this.finish( );
    }
}
