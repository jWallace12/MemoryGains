package a2.csci412.wwu.edu.memorygain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private int phraseTimer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager= new DatabaseManager( this );
    }

    protected void onResume( ) {
        super.onResume( );
    }

    public void goToPhraseRecall( View v ) {
        Intent myIntent = new Intent( this, PhraseRecall.class );
        this.startActivity(myIntent);
    }

    public void goToLocationRecall( View v ) {
        Intent myIntent = new Intent(this, LocationRecall.class);
        this.startActivity(myIntent);
    }

    public void goToImageRecall( View v ) {
        Intent myIntent = new Intent(this, ImageRecall.class);
        this.startActivity(myIntent);
    }

    public void goToStatistics( View v ) {
        Intent myIntent = new Intent(this, Statistics.class);
        this.startActivity(myIntent);
    }

    public void goToSettings( View v ) {
        Intent myIntent = new Intent(this, Settings.class);
        this.startActivity(myIntent);
    }

    public void phraseTimer() {

    }
}
