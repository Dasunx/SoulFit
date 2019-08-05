package com.dasun.soulfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int firstTime = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(firstTime ==1){
            Intent myIntent = new Intent(this, Welcome.class);
            firstTime = 0;
            startActivity(myIntent);
        }
    }

    public void onClickLogin(View v){
        Intent my = new Intent(this,Workouts.class);
        startActivity(my);
    }
}
