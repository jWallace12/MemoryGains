package a2.csci412.wwu.edu.memorygain;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by Jonah on 10/31/2017.
 */

public class Settings extends AppCompatActivity {
    private SharedPreferences sharedPreferences;            // saved data
    DatabaseManager dbManager;                              // database instance
    Switch notifSwitch, vibSwitch, locSwitch;
    EditText newPhraseTime, newLocationTime, newImageTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_settings);

        // get the saved values for the timers and write them to the screen
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        newPhraseTime = findViewById(R.id.newPhraseTime);
        newLocationTime = findViewById(R.id.newLocationTime);
        newImageTime = findViewById(R.id.newImageTime);
        newPhraseTime.setText(Integer.toString(sharedPreferences.getInt("newPhraseTime", 12)));
        newLocationTime.setText(Integer.toString(sharedPreferences.getInt("newLocationTime", 12)));
        newImageTime.setText(Integer.toString(sharedPreferences.getInt("newImageTime", 12)));

        notifSwitch = (Switch) findViewById(R.id.notifSwitch);
        vibSwitch = (Switch) findViewById(R.id.vibrateSwitch);
        locSwitch = (Switch) findViewById(R.id.locationSwitch);

        //Listener for the switches ie location, vibrate, notifications
        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                switch (v.getId()) {
                    case R.id.notifSwitch:
                        Log.w("listener", "notif");
                        if (notifSwitch.isChecked()) {
                            MainActivity.setNotification(true);
                            Toast.makeText( Settings.this, "Notifications on", Toast.LENGTH_SHORT ).show( );
                        } else {
                            MainActivity.setNotification(false);
                            Toast.makeText( Settings.this, "Notifications off", Toast.LENGTH_SHORT ).show( );
                        }
                        break;
                    case R.id.vibrateSwitch:
                        Log.w("listener", "vibe");
                        if (vibSwitch.isChecked()) {
                            MainActivity.setVibration(true);
                            Toast.makeText( Settings.this, "Vibrate on", Toast.LENGTH_SHORT ).show( );
                        } else {
                            MainActivity.setVibration(false);
                            Toast.makeText( Settings.this, "Vibrate off", Toast.LENGTH_SHORT ).show( );
                        }
                        break;
                    case R.id.locationSwitch:
                        Log.w("listener", "location");
                        if (locSwitch.isChecked()) {
                            MainActivity.setLocation(true);
                            Toast.makeText( Settings.this, "Location on", Toast.LENGTH_SHORT ).show( );
                        } else {
                            MainActivity.setLocation(false);
                            Toast.makeText( Settings.this, "Location off", Toast.LENGTH_SHORT ).show( );
                        }
                        break;
                }
            }
        };
        ((Switch) findViewById(R.id.notifSwitch)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.vibrateSwitch)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.locationSwitch)).setOnCheckedChangeListener(multiListener);
    }

    public void clearCache( View v) {
        Log.w("clear", "cache");
        //clear cache
    }

    // return timers to default time and delete all data
    public void reset( View v) {
        Log.w("reset", "reset");
        newPhraseTime.setText("12");
        newLocationTime.setText("12");
        newImageTime.setText("12");
        dbManager.deleteData();
    }

    // save the timer lengths and return to main screen
    public void goBack( View v ) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putInt("phraseTime", Integer.parseInt(newPhraseTime.getText().toString()));
            editor.putInt("locationTime", Integer.parseInt(newLocationTime.getText().toString()));
            editor.putInt("imageTime", Integer.parseInt(newImageTime.getText().toString()));
            editor.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.finish( );
    }


}
