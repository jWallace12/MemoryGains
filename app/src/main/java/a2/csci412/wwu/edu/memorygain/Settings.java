package a2.csci412.wwu.edu.memorygain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

/**
 * Created by Jonah on 10/31/2017.
 */

public class Settings extends AppCompatActivity{
    Button resetBtn;
    Button clrCacheBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Listener for the switches ie location, vibrate, notifications
        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                switch (v.getId()) {
                    case R.id.notifSwitch:
                        // Do something
                        break;
                    case R.id.vibrateSwitch:
                        // Do something
                        break;
                    case R.id.locationSwitch:
                        // Do something
                        break;
                }
            }
        };
    }

    public void clearCache( View v) {
        //clear cache
    }

    public void reset( View v) {
        //clear shit
    }

    public void goBack( View v ) {
        this.finish( );
    }
}
