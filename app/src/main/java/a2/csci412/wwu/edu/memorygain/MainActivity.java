package a2.csci412.wwu.edu.memorygain;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static boolean phraseTimer;
    private boolean locationTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phraseTimer = false;
        setContentView(R.layout.activity_main);
    }

    protected void onResume( ) {
        super.onResume( );

        // if a new phrase was just accepted, start a new timer
        if (phraseTimer) {
            setPhraseTimer(10000);
            phraseTimer = false;
        }
    }

    public void goToPhraseRecall( View v ) {
        Intent myIntent = new Intent(this,PhraseRecall.class );
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

    public void setPhraseTimer(int num) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+num, pendingIntent);
    }

    public static void setPhraseTimer() {
        phraseTimer = true;
    }

}
