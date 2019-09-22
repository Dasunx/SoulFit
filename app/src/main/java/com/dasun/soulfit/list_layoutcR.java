package com.dasun.soulfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class list_layoutcR extends AppCompatActivity {

    private Button upUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layoutc_r);

        upUser = findViewById(R.id.UpCr);

        upUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(list_layoutcR.this,Update_create_User.class));
            }
        });
    }
}
