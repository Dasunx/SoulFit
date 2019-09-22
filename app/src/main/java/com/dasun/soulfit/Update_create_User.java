package com.dasun.soulfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_create_User extends AppCompatActivity {

    private EditText newheight,newwWeight,newBirth;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_create__user);

        newheight = findViewById(R.id.upheight);
        newwWeight = findViewById(R.id.upweight);
        newBirth=findViewById(R.id.upBirth);
        save = findViewById(R.id.upsave);

        firebaseAuth =FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

         DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                newheight.setText(user.getHeight());
                newwWeight.setText(user.getWeight());
                newBirth.setText(user.getBirthday());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Update_create_User.this,databaseError.getCode(),Toast.LENGTH_LONG).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String heightR = newheight.getText().toString();
                String weightR = newwWeight.getText().toString();
                String BirthR = newBirth.getText().toString();

                User user = new User(BirthR,null,null,heightR,weightR);

                databaseReference.setValue(user);

                finish();
            }
        });
    }
}
