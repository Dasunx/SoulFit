package com.dasun.soulfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class addFood extends AppCompatActivity {

    //views
    EditText mFood,mCal;
    Button mAddBtn,mshowBtn;

    //progress dialog
    ProgressDialog pd;

    //Firestore instence
    FirebaseFirestore db;

    String pId,pfood,pcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //action bar and its title
        ActionBar actionBar = getSupportActionBar();


        //add

        //Initialize views
        mFood = findViewById(R.id.txtFood);
        mCal = findViewById(R.id.txtCal);
        mAddBtn = findViewById(R.id.btnAdd);
        mshowBtn = findViewById(R.id.btnShow);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            //update
            actionBar.setTitle("Update Data");
            mAddBtn.setText("Update");

            //getData
            pId = bundle.getString("pId");
            pfood = bundle.getString("pfood");
            pcal = bundle.getString("pcal");
            //set Data
            mFood.setText(pfood);
            mCal.setText(pcal);
        }
        else{
            // new data
            actionBar.setTitle("Add Data");
            mAddBtn.setText("Save");

        }

        //progress dialog
        pd = new ProgressDialog(this);

        //firestore
        db = FirebaseFirestore.getInstance();

        //click button to upload data
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle1 = getIntent().getExtras();
                if(bundle != null){
                    //updating
                    //input data
                    String id = pId;
                    String foodName = mFood.getText().toString().trim();
                    String callory = mCal.getText().toString().trim();
                    //function call to update data
                    updateData(id,foodName,callory);
                }
                else{
                    //adding new
                    //input data
                    String foodName = mFood.getText().toString().trim();
                    String callory = mCal.getText().toString().trim();
                    //function call to upload data
                    uploadData(foodName,callory);
                }




            }
        });

        //click button to show food lst
        mshowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addFood.this, showList.class));
                finish();
            }
        });

    }

    private void updateData(String id, String foodName, String callory) {
        //setTitle for the progress bar
        pd.setTitle("Updating Data");
        //Show progress bar when you click save button
        pd.show();

        db.collection("Documents").document(id)
                .update("foodName",foodName,"callory",callory)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when updated successfully
                        pd.dismiss();
                        Toast.makeText(addFood.this, "Updated..", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when error
                        pd.dismiss();
                        //get and show error
                        Toast.makeText(addFood.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData(String foodName, String callory) {

        //setTitle for the progress bar
        pd.setTitle("Adding food to the list");
        //Show progress bar when you click save button
        pd.show();
        //random id for each data to be stored
        String id = UUID.randomUUID().toString();

        Map<String,Object> doc = new HashMap<>();
        doc.put("id",id); //id of data
        doc.put("foodName",foodName);
        doc.put("callory",callory);

        //add these data
        db.collection("Documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //when data added successfuly
                        pd.dismiss();
                        Toast.makeText(addFood.this,"Uploaded successfully",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //when error occured
                        Toast.makeText(addFood.this, e.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
