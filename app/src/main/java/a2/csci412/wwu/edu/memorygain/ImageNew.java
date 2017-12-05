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
    private ImageView imageView;
    private TextView photoDescription;
    private Uri file;
    private String thisPhotoDescription;
    private String thisPhotoQuestion;

    private String[] photoRequirementList;
    private String[][] questionsList;
    private String[] peopleQuizList;
    private String[] automobileQuizList;
    private String[] animalQuizList;
    private String[] friendQuizList;
    private String[] youQuizList;
    private String[] satisfyQuizList;
    private String[] abstractQuizList;
    private String[] natureQuizList;
    private String[] bookQuizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_new);

        imageView = (ImageView) findViewById(R.id.imageview);
        photoDescription = (TextView) findViewById(R.id.photoDescription);

        // list of all photo requirements
        photoRequirementList = new String[] {"Take a picture of people", "Take a picture of a vehicle", "Take a picture of animal(s)",
                "Take a picture of your friends", "Take a picture of yourself", "Take a picture of something satisfying",
                "Take a picture of something abstract", "Take a picture of nature", "Take a picture of a book/text"};

        // the quiz list for each photo requirement
        peopleQuizList = new String[] {"How many people are in the picture?", "Who is the leftmost person in the picture?",
                "Who is the rightmost person in the picture?", "Do you know who's in the picture?"};
        automobileQuizList = new String[] {"What is the make/model of the vehicle in the picture?", "What color is the vehicle in the middle?",
                "Is the vehicle a car? Truck? SUV?", "Where is the vehicle parked?"};
        animalQuizList = new String[] {"What kind of animal is in the picture?", "What color is the animal in the picture?",
                "Where did you take the picture of the animal?", "What is the background behind the animal?"};
        friendQuizList = new String[] {"How many friends are in the picture?", "Name everyone in the picture?",
                "Where were your friends when you took the picture?", "What are your friends in the middle of doing?"};
        youQuizList = new String[] {"Where were you when you took the picture?", "What are you wearing in the picture?",
                "What were you doing before/after you took the picture?", "What is in the background of your picture?"};
        satisfyQuizList = new String[] {"What is the picture of?", "Why is the picture satisfying to you?",
                "What is the primary color of the item in this picture?", "What were you doing before/after you took the picture?"};
        abstractQuizList = new String[] {"What did you take a picture of?", "What color is the item in this picture?",
                "Why did you take this picture in particular?", "Where were you when you took this picture?"};
        natureQuizList = new String[] {"What did you take a picture of?", "Where were you when you took the picture?",
                "Approximately how many trees are in the picture?", "Which direction are you facing (N/S/E/W)?"};
        bookQuizList = new String[] {"What is the title of the book/text?", "Can you recall what the book/text says?",
                "Where were you when you took the picture?", "What were you doing before/after you took the picture?"};

        questionsList = new String[9][4];
        questionsList[0] = peopleQuizList;
        questionsList[1] = automobileQuizList;
        questionsList[2] = animalQuizList;
        questionsList[3] = friendQuizList;
        questionsList[4] = youQuizList;
        questionsList[5] = satisfyQuizList;
        questionsList[6] = abstractQuizList;
        questionsList[7] = natureQuizList;
        questionsList[8] = bookQuizList;

        // get a new photo description and question
        newPhotoQuestion();
        photoDescription.setText(thisPhotoDescription);
    }

    public void newPhotoQuestion() {
        //get random photo requirement and question
        Random generator = new Random();
        int requirementId = generator.nextInt(photoRequirementList.length-1);
        int questionId = generator.nextInt(3);

        thisPhotoDescription = photoRequirementList[requirementId];
        thisPhotoQuestion = questionsList[requirementId][questionId];
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
        editor.putString("imageQuestion", thisPhotoQuestion);
        editor.commit();

        startActivityForResult(intent, 100);
    }

    // save picture and return
    public void savePicture(View view) {
        MainActivity.setImageBoolean();
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
