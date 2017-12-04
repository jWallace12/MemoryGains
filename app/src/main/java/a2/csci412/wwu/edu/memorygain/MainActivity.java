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
    private static boolean vibration, notification, gps, phraseTimer, locationTimer, imageTimer,
                            phraseGuessReady, locationGuessReady, imageGuessReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load data
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        phraseTimer = sharedPreferences.getBoolean("phraseTimer", false);
        locationTimer = sharedPreferences.getBoolean("locationTimer", false);
        phraseGuessReady = sharedPreferences.getBoolean("phraseGuessReady", false);
        locationGuessReady = sharedPreferences.getBoolean("locationGuessReady", false);
        notification = sharedPreferences.getBoolean("notification", true);
        vibration = sharedPreferences.getBoolean("vibration", true);
        gps = sharedPreferences.getBoolean("gps", true);
        setContentView(R.layout.activity_main);
    }


    protected void onResume( ) {
        super.onResume( );

        // if a new recall was just completed, start the
        // corresponding timer
        if (phraseTimer) {
            setPhraseTimer();
            phraseTimer = false;
        } else if (locationTimer) {
            setLocationTimer();
            locationTimer = false;
        } else if (imageTimer) {
            setImageTimer();
            imageTimer = false;
        }
    }

    protected void onStop( ) {
        super.onStop( );
    }

    public void goToPhraseRecall( View v ) {
        Intent myIntent = new Intent(this,PhraseRecall.class );
        this.startActivity(myIntent);
    }

    // guess phrase if it is time
    public void goToPhraseGuess( View v ) {
        if (phraseGuessReady) {
            phraseGuessReady = false;
            Intent myIntent = new Intent(this, PhraseGuess.class);
            this.startActivity(myIntent);
        } else {
            Toast.makeText(this, "It is not time yet to guess a phrase.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToLocationRecall( View v ) {
        if (gps) {
            Intent myIntent = new Intent(this, LocationRecall.class);
            this.startActivity(myIntent);
        } else {
            Toast.makeText(this, "GPS services disabled. Turn on in Settings to use", Toast.LENGTH_SHORT).show();
        }
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

    public void goToImageNew( View v ) {
        Intent myIntent = new Intent(this, ImageNew.class);
        this.startActivity(myIntent);
    }

    public void goToImageRecall( View v ) {
        if (imageGuessReady) {
            imageGuessReady = false;
            Intent myIntent = new Intent(this, ImageRecall.class);
            this.startActivity(myIntent);
        } else {
            Toast.makeText(this, "It is not time yet for image recall.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToStatistics( View v ) {
        Intent myIntent = new Intent(this, Statistics.class);
        this.startActivity(myIntent);
    }

    public void goToSettings( View v ) {
        Intent myIntent = new Intent(this, Settings.class);
        this.startActivity(myIntent);
    }

    // set up timer for notification for phrase
    public void setPhraseTimer() {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, PhraseAlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + sharedPreferences.getInt("newPhraseTime", 12), pendingIntent);
    }

    // set up timer for notification for location
    public void setLocationTimer() {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, LocationAlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + sharedPreferences.getInt("newLocationTime", 12), pendingIntent);
    }

    // set up timer notification for image
    public void setImageTimer() {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, ImageAlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + sharedPreferences.getInt("newImageTime", 12), pendingIntent);
    }

    public static void setPhraseBoolean() { phraseTimer = true; }

    public static void setLocationBoolean() { locationTimer = true; }

    public static void setImageBoolean() { imageTimer = true; }

    public static void setNotification(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notification", b);
        editor.commit();
        notification = b;
    }

    public static void setVibration(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("vibration", b);
        editor.commit();
        vibration = b;
    }

    public static void setGPS(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("gps", b);
        editor.commit();
        gps = b;
    }

    public static void setPhraseGuessReady(boolean setter) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("phraseGuessReady", setter);
        editor.commit();
        phraseGuessReady = setter;
    }

    public static void setLocationGuessReady(boolean setter) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("locationGuessReady", setter);
        editor.commit();
        locationGuessReady = setter;
    }

    public static void setImageGuessReady(boolean setter) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("imageGuessReady", setter);
        editor.commit();
        imageGuessReady = setter;
    }

}
