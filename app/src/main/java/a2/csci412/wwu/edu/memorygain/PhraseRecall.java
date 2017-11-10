package a2.csci412.wwu.edu.memorygain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jonah on 10/31/2017.
 */

public class PhraseRecall extends AppCompatActivity{
    private TextView givenPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_recall);

        givenPhrase = (TextView) findViewById(R.id.givenPhraseText);
    }

    public void newPhrase(View v) {
        //get random phrase
        givenPhrase.setText("hello");
    }

    public void goBack( View v ) {
        this.finish( );
    }
}
