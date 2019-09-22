package com.dasun.soulfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class showList extends AppCompatActivity {

    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    //layout manager for recycle view
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;

    //firestore instence
    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        //action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Your Food List ");

        //init firestore
        db = FirebaseFirestore.getInstance();

        //initialize views
        mRecyclerView = findViewById(R.id.recycler_view);
        mAddBtn = findViewById(R.id.addBtn);


        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //init progress dialog
        pd = new ProgressDialog(this);

        //showdata in recycler view
        showData();

        //handle floating action btn
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(showList.this,addFood.class));
                finish();
            }
        });

    }

    private void showData() {

        //set title of progress dialog
        pd.setTitle("Loading Data..");
        //show progress dialog
        pd.show();

        db.collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();
                        //called when data is retieved
                        pd.dismiss();

                        //show data
                        for(DocumentSnapshot doc:task.getResult()){

                            Model model = new Model(doc.getString("id"),
                            doc.getString("foodName"),
                            doc.getString("callory"));

                            modelList.add(model);

                        }
                        //adapter
                        adapter = new CustomAdapter(showList.this,modelList);
                        //set adapter to recycler view
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when error occured
                        pd.dismiss();
                        Toast.makeText(showList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteData(int index){

        //set title of progress dialog
        pd.setTitle("Deleting Data..");
        //show progress dialog
        pd.show();
        db.collection("Documents").document(modelList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when deleted successfully
                        Toast.makeText(showList.this, "Deleted..", Toast.LENGTH_SHORT).show();
                        //update date
                        showData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //calling when there is an error
                        pd.dismiss();

                        //get and show error msg
                        Toast.makeText(showList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
