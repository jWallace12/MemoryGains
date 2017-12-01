package a2.csci412.wwu.edu.memorygain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Jonah on 10/31/2017.
 */

public class ImageNew extends AppCompatActivity{
    private SharedPreferences sharedPreferences;
    private Button takePictureButton;
    private ImageView imageView;
    private TextView photoDescription;
    private Uri file;
    private String[] questionList;
    private String thisPhotoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_new);

        takePictureButton = (Button) findViewById(R.id.takePicture);
        imageView = (ImageView) findViewById(R.id.imageview);
        photoDescription = (TextView) findViewById(R.id.photoDescription);

        questionList = new String[] {"Take a picture of people", "Take a picture of automobiles", "Take a picture of animal(s)",
                "Take a picture with friends", "Take a picture of nature", "Take a picture of something satisfying",
                "Take a picture of something abstract", "Take a picture of something frustrating", "Take a picture of a book/text"};

        thisPhotoDescription = newPhotoDescription();
        photoDescription.setText(thisPhotoDescription);
    }

    public String newPhotoDescription() {
        //get random phrase
        Random generator = new Random();
        thisPhotoDescription = questionList[generator.nextInt(questionList.length-1)];
        return thisPhotoDescription;
    }

    // take a picture
    public void takePicture(View view) {
        // load shared data
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // create an intent for a new photo event
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        // store the photo URI in shared data
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", file.toString());
        editor.putString("imageDescription", thisPhotoDescription);
        editor.commit();

        startActivityForResult(intent, 100);
    }

    // save picture and return
    public void savePicture(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle take photo
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(file);
            }
        }
    }

    // generate a new image file in memory
    private static File getOutputMediaFile(){
        // attempt to create file
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MemoryGains");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        // append timestamp to make unique filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
}
