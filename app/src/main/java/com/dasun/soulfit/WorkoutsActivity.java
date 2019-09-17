package com.dasun.soulfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutsActivity extends AppCompatActivity {

    ImageView btn;
    TextView burnedCal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        btn = findViewById(R.id.imageView5);
        burnedCal=findViewById(R.id.textViewCalBurned);

    }

    public void goBack(View view){
        Intent my = new Intent(this,user_create.class);
        startActivity(my);
    }

    public void calBurned(View v){
        burnedCal.setText("+"+200);
    }

    public void createWorkout(View view){
        Intent next=new Intent(this,createWorkoutActivity.class);
        startActivity(next);
    }

}
