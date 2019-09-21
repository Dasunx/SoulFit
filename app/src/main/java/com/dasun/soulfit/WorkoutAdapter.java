package com.dasun.soulfit;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*In here we need 2 classes
* 1. RecyclerView Adapter class - to bind the data
* 2. RecyclerView ViewHolder class - this class will hold the view
* */
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>{
    private Context mctx;
    private List<Workout> listWorkout;
    /*for Btn click event in the recycler view*/
    private OnItemClickListner mListner;
    public interface OnItemClickListner{
        void onBtnClick(int position);/*for the add button*/
        void onWorkoutItemClick(int position);/*for whole item*/
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }

    public WorkoutAdapter(Context mctx, List<Workout> listWorkout) {
        this.mctx = mctx;
        this.listWorkout = listWorkout;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mctx);
        View view=inflater.inflate(R.layout.workoutrow,null);
        WorkoutViewHolder wHolder=new WorkoutViewHolder(view);
        return wHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout=listWorkout.get(position);

        holder.txtTitle.setText(workout.getName());
        holder.txtMins.setText(String.valueOf(workout.getMinute()));
        holder.txtCal.setText(String.valueOf(workout.getCalorie()));


    }

    @Override
    public int getItemCount() {
        return listWorkout.size();
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBg;
        TextView txtTitle,txtMins,txtCal;
        Button btnworkoutAdd;
        public WorkoutViewHolder(View itemView) {
            super(itemView);
            imgBg=itemView.findViewById(R.id.imageViewWorkoutContainer);
            txtTitle=itemView.findViewById(R.id.textViewWorkoutTitle);
            txtMins=itemView.findViewById(R.id.textViewMinutes);
            txtCal=itemView.findViewById(R.id.textViewCalorie);
            btnworkoutAdd=itemView.findViewById(R.id.btnWorkout_add);
            /*btn onclick listner Add button*/
            btnworkoutAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner != null){
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListner.onBtnClick(position);
                        }
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner != null){
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListner.onWorkoutItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
