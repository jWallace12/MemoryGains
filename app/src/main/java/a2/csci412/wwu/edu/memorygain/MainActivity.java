package a2.csci412.wwu.edu.memorygain;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static SharedPreferences sharedPreferences;
    private static boolean phraseTimer;
    private static boolean locationTimer;
    private static boolean phraseGuessReady;
    private static boolean locationGuessReady;
    private static boolean vibration, notification, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        phraseTimer = sharedPreferences.getBoolean("phraseTimer", false);
        locationTimer = sharedPreferences.getBoolean("locationTimer", false);
        phraseGuessReady = sharedPreferences.getBoolean("phraseGuessReady", false);
        locationGuessReady = sharedPreferences.getBoolean("locationGuessReady", false);
        setContentView(R.layout.activity_main);
    }


    protected void onResume( ) {
        super.onResume( );

        // if a new phrase was just accepted, start a new timer
        if (phraseTimer) {
            setPhraseTimer(100);
            phraseTimer = false;
        } else if (locationTimer) {
            setLocationTimer(100);
            locationTimer = false;
        }
    }
    protected void onStop( ) {
        super.onStop( );
    }

    public void goToPhraseRecall( View v ) {
        Intent myIntent = new Intent(this,PhraseRecall.class );
        this.startActivity(myIntent);
    }

    public void goToPhraseGuess( View v ) {
        if (phraseGuessReady) {
            Intent myIntent = new Intent(this, PhraseGuess.class);
            this.startActivity(myIntent);
        } else {
            Toast.makeText(this, "It is not time yet to guess a phrase.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToLocationRecall( View v ) {
        Intent myIntent = new Intent(this, LocationRecall.class);
        this.startActivity(myIntent);
    }

    public void goToLocationGuess( View v ) {
        if (locationGuessReady) {
            locationGuessReady = false;
            Intent myIntent = new Intent(this, LocationGuess.class);
            this.startActivity(myIntent);
        } else {
            Toast.makeText(this, "It is not time yet to guess a location.", Toast.LENGTH_SHORT).show();
        }
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

    // set up timer for notification to
    public void setPhraseTimer(int time) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, PhraseAlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + time, pendingIntent);
    }

    public void setLocationTimer(int time) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, LocationAlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + time, pendingIntent);
    }

    public static void setPhraseBoolean() {
        phraseTimer = true;
    }

    public static void setLocationBoolean() { locationTimer = true; }

    public static void setNotification(boolean b) {
        notification = b;
    }

    public static void setVibration(boolean b) {
        locationTimer = b;
    }

    public static void setLocation(boolean b) {
        locationTimer = b;
    }

    public static void setPhraseGuessReady(boolean setter) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("phraseGuessReady", setter);
        editor.commit();
        phraseGuessReady = setter;
    }

    public static void setLocationGuessReady() {locationGuessReady = true;}

}
