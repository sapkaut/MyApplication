package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent choose = new Intent(this,ProfileActivity.class);
        Button chooseProfile = (Button)findViewById(R.id.chooseProfile);
        chooseProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(choose);
            }
        });
    }


}