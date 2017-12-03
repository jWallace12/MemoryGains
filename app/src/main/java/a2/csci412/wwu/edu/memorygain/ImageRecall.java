package a2.csci412.wwu.edu.memorygain;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


/**
 * Created by Jonah on 10/31/2017.
 */

public class ImageRecall extends AppCompatActivity{
    private SharedPreferences sharedPreferences;
    private DatabaseManager dbManager;
    private Uri file;
    private TextView photoDescription;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_recall);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        dbManager = new DatabaseManager(this);
        photoDescription = (TextView) findViewById(R.id.photoDescription);

        String imageString = sharedPreferences.getString("image", null);
        String imageDescription = sharedPreferences.getString("imageDescription", null);
        String imageQuestion = sharedPreferences.getString("imageQuestion", null);

        // parse image and make invisible
        file = Uri.parse(imageString);
        ImageView image = (ImageView) findViewById(R.id.imageview);
        image.setImageURI(file);
        image.setVisibility(View.GONE);

        photoDescription.setText("Photo: " + imageDescription + "\n\nRecall question: " + imageQuestion);
    }

    // show the image
    public void showImage() {
        ImageView image = (ImageView) findViewById(R.id.imageview);
        image.setVisibility(View.VISIBLE);
    }

    // log a correct guess from the user
//    public void makeGuess(View view) {
//        // change button visibility
//        //Button makeGuess = findViewById(R.id.btn_make_guess);
//        Button guessCorrect = findViewById(R.id.btn_guess_correct);
//        //Button guessIncorrect = findViewById(R.id.btn_guess_incorrect);
//
//        makeGuess.setVisibility(View.GONE);
//        guessCorrect.setVisibility(View.VISIBLE);
//        guessIncorrect.setVisibility(View.VISIBLE);
//
//        // show the image
//        showImage();
//    }

    // log a correct guess from the user
    public void guessCorrect(View view) {
        dbManager.insert(new Recall(0, "image", "pass"));
        clearImageRecall();
        finish();
    }

    // log an incorrect guess from the user
    public void guessIncorrect(View view) {
        dbManager.insert(new Recall(0, "image", "fail"));
        clearImageRecall();
        finish();
    }

    // clear image recall
    public void clearImageRecall() {
        // delete the picture
        File fdelete = new File(file.getPath());
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + file.getPath());
            }
        }

        // clear shared prefs
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", null);
        editor.putString("imageDescription", null);
        editor.putString("imageQuestion", null);
        editor.commit();
    }
}
