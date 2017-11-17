package a2.csci412.wwu.edu.memorygain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by Jonah on 10/31/2017.
 */

public class Settings extends AppCompatActivity{
    Button resetBtn, clrCacheBtn;
    Switch notifSwitch, vibSwitch, locSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
                            Toast.makeText( Settings.this, "Notifications off", Toast.LENGTH_SHORT ).show( );
                        }

                        // Do something
                        break;
                    case R.id.vibrateSwitch:
                        Log.w("listener", "vibe");
                        Toast.makeText( Settings.this, "Vibrate ", Toast.LENGTH_SHORT ).show( );
                        // Do something
                        break;
                    case R.id.locationSwitch:
                        Log.w("listener", "location");
                        Toast.makeText( Settings.this, "Location", Toast.LENGTH_SHORT ).show( );
                        // Do something
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

    public void reset( View v) {
        Log.w("reset", "reset");
        //clear shit
    }

    public void goBack( View v ) {
        this.finish( );
    }
}
