package a2.csci412.wwu.edu.memorygain;

import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Jonah on 10/31/2017.
 */

public class ImageRecall extends AppCompatActivity{
    private SharedPreferences sharedPreferences;
    private Uri file;
    private String[] questionList;
    private TextView photoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_recall);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        photoDescription = (TextView) findViewById(R.id.photoDescription);

        String imageString = sharedPreferences.getString("image", null);
        String imageDescription = sharedPreferences.getString("imageDescription", null);

        ImageView image = (ImageView) findViewById(R.id.imageview);
        file = Uri.parse(imageString);

        image.setImageURI(file);
        photoDescription.setText(imageDescription);
    }
}
