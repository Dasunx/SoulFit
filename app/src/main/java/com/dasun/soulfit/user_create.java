package com.dasun.soulfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_create extends AppCompatActivity {
    EditText Birthday;
    RadioButton male;
    RadioButton female;
    EditText height;
    EditText weight;
    Button submit;

    DatabaseReference dbref;
    User user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        Birthday =findViewById(R.id.Birthday);
        male =  findViewById(R.id.male);
        female =  findViewById(R.id.female);
        height =  findViewById(R.id.height);
        weight =  findViewById(R.id.weight);

        submit = findViewById(R.id.submit);

        user1 = new User();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref = FirebaseDatabase.getInstance().getReference().child("User");
                user1.setBirthday(Birthday.getText().toString().trim());
                user1.setFemale(female.getText().toString().trim());
                user1.setMale(male.getText().toString().trim());
                user1.setHeight(height.getText().toString().trim());
                user1.setWeight(weight.getText().toString().trim());

                dbref.push().setValue(user1);
                Toast.makeText(getApplicationContext(),"Sucsess",Toast.LENGTH_LONG);

            }
        });
    }
}
