package a2.csci412.wwu.edu.memorygain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jonah on 11/13/2017.
 */

public class PhraseGuess extends AppCompatActivity {
    private String answer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answer = PhraseRecall.getCurrentPhrase();
        setContentView(R.layout.activity_guess_phrase);
    }

    public void guess() {

    }
}
