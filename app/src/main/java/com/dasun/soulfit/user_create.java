package com.dasun.soulfit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_create extends AppCompatActivity {
    EditText Birthday;
    Spinner gender;
    EditText height;
    EditText weight;
    Button submit ,up;

    DatabaseReference dbCuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        dbCuser = FirebaseDatabase.getInstance().getReference("User");

        Birthday =(EditText) findViewById(R.id.Birthday);
        gender = (Spinner)findViewById(R.id.spinnergender);
        height =  (EditText)findViewById(R.id.height);
        weight =  (EditText)findViewById(R.id.weight);

        submit = (Button) findViewById(R.id.submit);
        up = findViewById(R.id.UpCrr);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newpage();

                addUDetails();



            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(user_create.this,Update_create_User.class));
            }
        });

    }
    private void newpage(){
        Intent s = new Intent(this,ViweCreateUser.class);
        startActivity(s);
    }
    private void addUDetails(){
        String birthday = Birthday.getText().toString().trim();
        String Gender = gender.getSelectedItem().toString();
        String Height = height.getText().toString().trim();
        String Weight = weight.getText().toString().trim();

        if (!TextUtils.isEmpty(birthday)){

            String id = dbCuser.push().getKey();

            User user = new User(birthday,Gender,id,Height,Weight);

            dbCuser.child(id).setValue(user);

            Toast.makeText(this,"Successfully add",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"You should enter Birthday",Toast.LENGTH_LONG).show();
        }
    }
}
