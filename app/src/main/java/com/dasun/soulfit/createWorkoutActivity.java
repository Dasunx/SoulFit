package com.dasun.soulfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createWorkoutActivity extends AppCompatActivity {

    EditText workoutName;
    EditText minutes;
    EditText calories;
    Button btncreate;
    DatabaseReference df;
    Workout wko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        workoutName =findViewById(R.id.editText_workout_name);
        minutes=findViewById(R.id.editText_minutes);
        calories=findViewById(R.id.editText_burned_cal);
        btncreate=findViewById(R.id.btn_create);

        wko =new Workout();

    }
    private void clearControls(){
        workoutName.setText("");
        minutes.setText("");
        calories.setText("");
    }
    public void createWorkouts(View view){
        df=FirebaseDatabase.getInstance().getReference().child("Workout");


        if(TextUtils.isEmpty(workoutName.getText().toString())){
            Toast.makeText(getApplicationContext(),"please enter a Name",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(minutes.getText().toString())){
            Toast.makeText(getApplicationContext(),"please enter minutes",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(calories.getText().toString())){
            Toast.makeText(getApplicationContext(),"Enter value for calories",Toast.LENGTH_SHORT).show();
        }else{
            wko.setName(workoutName.getText().toString().trim());
            double dMinute;
            double dCalorie;

            //====== Minute Error Handling and assigning part
            String sMinute =minutes.getText().toString();
            if(!sMinute.isEmpty()) {
                try {
                    dMinute = Double.parseDouble(sMinute);
                    // it means it is double
                    wko.setMinute(dMinute);//Assign it to the Workout object
                } catch (Exception e1) {
                    // this means it is not double
                    Toast.makeText(getApplicationContext(), "Only numericals allows.", Toast.LENGTH_SHORT).show();
                }
            }//minute error handling end
            //====== Calories Error Handling and assigning part
            String sCalorie=calories.getText().toString();
            if(!sCalorie.isEmpty()){
                try{
                    dCalorie=Double.parseDouble(sCalorie);
                    wko.setCalorie(dCalorie);//Assign it to the Workout object
                }catch (Exception cal){
                    Toast.makeText(getApplicationContext(),"only numericals allowed",Toast.LENGTH_SHORT).show();
                }
            }//======end Error handling assigning

            //Insert into DB
            df.push().setValue(wko);
            //Feedback to the user Via Toast
            Toast.makeText(getApplicationContext(),"Workout successfully added.",Toast.LENGTH_SHORT).show();
            //Clearing text fields
            clearControls();
        }
    }



}
