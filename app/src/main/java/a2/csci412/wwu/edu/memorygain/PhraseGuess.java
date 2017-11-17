package a2.csci412.wwu.edu.memorygain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jonah on 11/13/2017.
 */

public class PhraseGuess extends AppCompatActivity {
    private DatabaseManager dbManager;
    private String answer;
    private int guesses;
    private boolean hint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        answer = PhraseRecall.getCurrentPhrase();
        guesses = 3;
        hint = true;
        setContentView(R.layout.activity_guess_phrase);
    }

    // process the user's guess
    public void guessPhrase(View v) {

        // get the guess
        EditText guessText = ( EditText ) findViewById(R.id.phraseGuess);
        String userGuess = guessText.getText().toString();

        // if the user guessed correctly, tell the user they were successfully, and add a successful
        // entry into the database

        if (userGuess.equals(answer)) {
            Toast.makeText(this, "Congratulations! You recalled the phrase!", Toast.LENGTH_LONG);
            dbManager.insert(new Recall(0, "phrase", "pass"));

        // else, decrement remaining guesses
        // if all guesses are gone, tell the user they failed, and add an unsuccessful entry into the database
        } else {
            guesses--;
            if (guesses == 0) {
                Toast.makeText(this, "Sorry, incorrect. You have run out of guesses. Try again!", Toast.LENGTH_LONG);
                dbManager.insert(new Recall(0, "phrase", "fail"));
                this.finish();
            } else {
                Toast.makeText(this, "Sorry, incorrect! You have " + guesses + " guesses remaining", Toast.LENGTH_SHORT);
            }
        }
    }

    // give the user the first word in the phrase
    public void giveHint(View v) {
        if (hint) {
            String[] splitter = answer.split(" ");
            Toast.makeText(this, "The first word in the expected phrase is: " + splitter[0], Toast.LENGTH_LONG);
            hint = false;
        }
    }

    // finish this activity
    public void goBack(View v) {
        this.finish();
    }
}
