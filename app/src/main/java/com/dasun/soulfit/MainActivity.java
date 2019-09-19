package com.dasun.soulfit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp= getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int fTime = sp.getInt("fTime",0);

        if (fTime!=1){
            Intent myn = new Intent(this,Welcome.class);
            startActivity(myn);
            SharedPreferences.Editor spEdit= sp.edit();
            spEdit.putInt("fTime",1);
            spEdit.commit();

        }else{
            Intent myn = new Intent(this,Login.class);
            startActivity(myn);
        }



    }


}
