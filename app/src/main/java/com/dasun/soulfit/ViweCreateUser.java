package com.dasun.soulfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViweCreateUser extends AppCompatActivity {

    ListView listviwecreateUser;
    DatabaseReference dbCuser;

    ArrayList<String>arrayList=new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viwe_create_user);
        dbCuser = FirebaseDatabase.getInstance().getReference("User");

        listviwecreateUser =(ListView)findViewById(R.id.listviewCreateUser);

        userList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbCuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();
                for (DataSnapshot cuserSnapshot: dataSnapshot.getChildren()){

                    User user = cuserSnapshot.getValue(User.class);

                    userList.add(user);
                }

                cUserList adapter = new cUserList(ViweCreateUser.this,userList);
                listviwecreateUser.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
