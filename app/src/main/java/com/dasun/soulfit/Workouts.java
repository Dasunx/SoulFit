package com.dasun.soulfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Workouts extends AppCompatActivity {

    ImageView btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        btn = findViewById(R.id.imageView5);

    }

    public void goBack(View view){
        Intent my = new Intent(this,CallorieCalc.class);
        startActivity(my);
    }

}
