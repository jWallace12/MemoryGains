package a2.csci412.wwu.edu.memorygain;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jonah on 11/13/2017.
 */

public class LocationGuess extends AppCompatActivity {
    private SharedPreferences sharedPreferences;    // saved data
    private DatabaseManager dbManager;              // database to update
    private String answer;                          // correct answer
    private String oldTime;                         // time taken during location snapshot
    private int guesses;
    private boolean hint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        answer = sharedPreferences.getString("location", null);
        oldTime = sharedPreferences.getString("time", null);
        guesses = 3;
        hint = true;
        setContentView(R.layout.activity_guess_location);

        // remind the user at what time that snapshot was taken
        TextView heading = (TextView) findViewById(R.id.locationGuessMessage);
        heading.setText("Think back hard... Where were you at " + oldTime + "?");

    }

    // process the user's guess
    public void guess(View v) {

        // get the location
        EditText guessText = ( EditText ) findViewById(R.id.locationGuess);
        String userGuess = guessText.getText().toString();

        // if the user guessed correctly, tell the user they were successfully, and add a successful
        // entry into the database

        if ((userGuess != null) && (userGuess.equals(answer))) {
            Toast.makeText(this, "Congratulations! You recalled the location!", Toast.LENGTH_LONG).show();
            dbManager.insert(new Recall(0, "location", "pass"));
            MainActivity.setLocationGuessReady(false);
            this.finish();

        // else, decrement remaining guesses
        // if all guesses are gone, tell the user they failed, and add an unsuccessful entry into the database

        } else {
            guesses--;
            if (guesses == 0) {
                Toast.makeText(this, "Sorry, incorrect. You have run out of guesses. Try again!", Toast.LENGTH_LONG).show();
                dbManager.insert(new Recall(0, "location", "fail"));
                MainActivity.setLocationGuessReady(false);
                this.finish();
            } else {
                Toast.makeText(this, "Sorry, incorrect! You have " + guesses + " guesses remaining", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // give the user the first word in the address
    public void giveHintLocation(View v) {
        if (hint) {
            String[] splitter = answer.split(" ");
            Toast.makeText(this, "The first word in the expected location is: " + splitter[0], Toast.LENGTH_LONG).show();
            hint = false;
        }
    }

    public void goBack(View v){
        this.finish();
    }
}
