package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private final int Pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView = (ImageView)findViewById(R.id.imageView);
        ImageButton PickImage = (ImageButton)findViewById((R.id.galery));
        ImageButton NotePad = (ImageButton)findViewById(R.id.notepad);
        ImageButton Calendar = (ImageButton)findViewById(R.id.calendar);
        ImageButton News = (ImageButton)findViewById(R.id.news);
        ImageButton Camera = (ImageButton) findViewById(R.id.Camera);
        ImageButton Weather = (ImageButton) findViewById(R.id.weather);
        Intent notePadIntent = new Intent(this,NotePadActivity.class);
        Intent calendarIntent = new Intent(this,CalendarActivity.class);
        Intent newsIntent = new Intent(this,NewsActivity.class);
        Intent camIntent = new Intent(this,CameraActivity.class);
        Intent weatherIntent = new Intent(this,Weather.class);

        PickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,Pick_image);
            }
        });

        NotePad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(notePadIntent);
            }
        });

        Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(calendarIntent);
            }
        });

        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newsIntent);
            }
        });

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(camIntent);
            }
        });

        Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(weatherIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);

        switch(requestCode){
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try{
                        final Uri imageUri = imageReturnedIntent.getData();
                       final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                       final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        //final Drawable imagee =
                        imageView.setImageBitmap(selectedImage);

                    }catch (FileNotFoundException e){
                        e.printStackTrace();;
                    }
                }
        }
    }
}