package com.dasun.soulfit;

import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WorkoutAdapter workoutAdapter;
    List<Workout> workoutList;
    DatabaseReference dbShowCompleted;
    DatabaseReference dbScheduleRef;
    DatabaseReference dbListShowRef;
    Schedule newDaySchedule;


    ImageView btn;
    TextView burnedCal,workoutCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        dbListShowRef= FirebaseDatabase.getInstance().getReference("Workout");
        dbScheduleRef= FirebaseDatabase.getInstance().getReference("workoutSchedule");

        workoutList =new ArrayList<>();
        recyclerView=findViewById(R.id.recycleViewWorkout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*Showing single Row Db Burned Cals and Completed Workouts*/

        dbShowCompleted=FirebaseDatabase.getInstance().getReference("workoutSchedule").child("-Lp8BrKKaPR1PKovCru8");
        dbShowCompleted.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String burnedCals=dataSnapshot.child("caloriesBurned").getValue().toString();
                String completedWorkouts=dataSnapshot.child("workoutsCount").getValue().toString();

                workoutCounter.setText(completedWorkouts);
                burnedCal.setText(burnedCals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*END - Showing single Row Db Burned Cals and Completed Workouts*/

        btn = findViewById(R.id.imageView5);
        burnedCal=findViewById(R.id.textViewCalBurned);
        workoutCounter=findViewById(R.id.TextViewDoneCount);

    }

    @Override
    protected void onStart() {
        super.onStart();

        /*Showing the workout list*/

        dbListShowRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                workoutList.clear();

                for (DataSnapshot workoutSnapList:dataSnapshot.getChildren()){
                    //Workout wkouts=workoutSnapList.getValue(Workout.class);
                    //workoutList.add(wkouts);

                    String wName=workoutSnapList.child("name").getValue().toString();
                    String wCal=workoutSnapList.child("calorie").getValue().toString();
                    String wMin=workoutSnapList.child("minute").getValue().toString();
                    String wId=workoutSnapList.child("workoutId").getValue().toString();

                    double wCalD=Double.parseDouble(wCal);
                    double wMinD=Double.parseDouble(wMin);

                    Workout wkouts=new Workout(wId,wName, wMinD, wCalD);
                    workoutList.add(wkouts);

                }

                workoutAdapter=new WorkoutAdapter(WorkoutsActivity.this,workoutList);
                recyclerView.setAdapter(workoutAdapter);
                /*workout onclick event for update burned cals and completed workouts*/
                workoutAdapter.setOnItemClickListner(new WorkoutAdapter.OnItemClickListner() {
                    @Override
                    public void onWorkoutItemClick(int position) {
                        Workout wkfromDb=workoutList.get(position);
                        updateWorkout(wkfromDb.getWorkoutId(),wkfromDb.getName(),wkfromDb.getCalorie(),wkfromDb.getMinute());
                    }

                    @Override
                    public void onBtnClick(int position) {
                        /*Calories Calculator*/
                        Double cal=workoutList.get(position).getCalorie();

                        Double tCal = Double.parseDouble(burnedCal.getText().toString());
                        tCal+=cal;
                        String tmpCal=Double.toString(tCal);
                        burnedCal.setText(tmpCal);
                        /*Exercises Count Calculator*/
                        int eCount=Integer.parseInt(workoutCounter.getText().toString());
                        eCount++;
                        String sECount=Integer.toString(eCount);
                        workoutCounter.setText(sECount);
                        /*Date*/
                        /*
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        workoutCounter.setText(date);
                        */

                        /*adding burned Cals and completed workouts to the db */

                        DatabaseReference dbRefSingleRow=FirebaseDatabase.getInstance().getReference("workoutSchedule").child("-Lp8BrKKaPR1PKovCru8");
                        Schedule singleRowShedule=new Schedule();
                        singleRowShedule.setCaloriesBurned(tCal);
                        singleRowShedule.setWorkoutsCount(eCount);
                        dbRefSingleRow.setValue(singleRowShedule);


                        /*END - adding burned cals and completed workouts to the db*/
                    }
                });/*END - workout onclick event for update burned cals and completed workouts*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        /*END Showing the workout list*/



    }

    public void goBack(View view){
        Intent my = new Intent(this,user_create.class);
        startActivity(my);
    }

    public void calBurned(View v){

        burnedCal.setText("+"+200);
    }

    public void resetDay(View v){
        newDaySchedule=new Schedule();
        burnedCal.setText("0");
        workoutCounter.setText("0");
        newDaySchedule.setCaloriesBurned(0);
        newDaySchedule.setWorkoutsCount(0);
        String id=dbScheduleRef.push().getKey();
        newDaySchedule.setScheuleId(id);
        dbScheduleRef.child(id).setValue(newDaySchedule);

        Toast.makeText(this,"New Schedule Assigned",Toast.LENGTH_LONG).show();

    }

    public void createWorkout(View view){
        Intent next=new Intent(this,createWorkoutActivity.class);
        startActivity(next);
    }

    /*This function will show the update dialog box*/
    public void updateWorkout(final String workoutId, String workoutName, double cal, double min){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText edTxtUpdateName=dialogView.findViewById(R.id.editTextUpdateName);
        final EditText edTxtUpdateMinute=dialogView.findViewById(R.id.editTextUpdateMinute);
        final EditText edTxtUpdateCals=dialogView.findViewById(R.id.editTextUpdateCalories);
        final Button btnUpdate=dialogView.findViewById(R.id.btn_w_update);
        final Button btnDelete=dialogView.findViewById(R.id.btn_w_delete);

        dialogBuilder.setTitle("Update  "+workoutName);

        edTxtUpdateCals.setText(String.valueOf(cal));
        edTxtUpdateMinute.setText(String.valueOf(min));
        edTxtUpdateName.setText(workoutName);

        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edTxtUpdateName.getText().toString().trim();
                String min=edTxtUpdateMinute.getText().toString().trim();
                String cal=edTxtUpdateCals.getText().toString().trim();
                double minD=Double.parseDouble(min);
                double calD=Double.parseDouble(cal);
                funcUpdateWorkout(workoutId,name,minD,calD);
                alertDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funcDeleteWorkout(workoutId);
                alertDialog.dismiss();
            }
        });



    }

    private boolean funcDeleteWorkout(String workoutId) {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Workout").child(workoutId);
        databaseReference.removeValue();
        Toast.makeText(this,"Workout Deleted Successfully.",Toast.LENGTH_LONG).show();
        return true;
    }

    /*function for the update, This will call inside the updateWorkout big function*/
    private boolean funcUpdateWorkout(String id,String name,double min,double cal){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Workout").child(id);
        Workout workoutUpd=new Workout(id,name,min,cal);
        databaseReference.setValue(workoutUpd);
        Toast.makeText(this,"Workout updated succesfully.",Toast.LENGTH_LONG).show();
        return  true;
    }
}
