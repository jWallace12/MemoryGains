package a2.csci412.wwu.edu.memorygain;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Jonah on 10/31/2017.
 */

public class PhraseRecall extends AppCompatActivity{
    SharedPreferences sharedPreferences;
    private TextView givenPhrase;
    private String[] phraseList;
    private static String currentPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_recall);

        // retrieve the saved wor
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentPhrase = sharedPreferences.getString("phrase", "Default");
        givenPhrase = (TextView) findViewById(R.id.givenPhraseText);
        TextView testing = (TextView) findViewById(R.id.tester);
        testing.setText(currentPhrase);

        // load available phrases
        phraseList = new String[] {"hello there", "Don't forget me", "Today is the best day", "Find out for yourself",
                "Keep your head above water", "I'm always here for you", "Off to grandma's house we go", "You can't handle yourself",
                "You don't know what you are saying", "Remembering is fun!", "I could never forget", "I'll never forget you",
                "You are the light of my life", "I am very proud of you", "Where there is smoke there is fire", "I love you",
                "You don't even know the half of it", "I forgive you", "Everything will be just fine", "Take my hand",
                "Run as fast as you can", "I believe in you", "Don't worry about it", "I knew that", "It's the end of the world",
                "Dont be afraid", "Never say never", "It's a diamond in the rough", "You're a one in a million", "The odds are against us"};
    }


    public void newPhrase(View v) {
        //get random phrase
        Random generator = new Random();
        givenPhrase.setText(phraseList[generator.nextInt(phraseList.length-1)]);
    }

    // erase saved phrase
    public void resetPhrase() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phrase", null);
        editor.commit();
    }

    // accept new phrase
    public void acceptPhrase(View v) {

        // delete old phrase
        resetPhrase();

        // retrieve the phrase, and then save it into SharedPreferences
        currentPhrase = givenPhrase.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phrase", currentPhrase);
        editor.commit();

        // tell main activity that a new timer is being created, and
        // return to main activity
        MainActivity.setPhraseBoolean();
        goBack();
    }

    public static String getCurrentPhrase() {
        return currentPhrase;
    }

    // finish activity
    public void goBack() {
        this.finish( );
    }


}
