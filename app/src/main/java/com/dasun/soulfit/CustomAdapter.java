package com.dasun.soulfit;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    showList shwList;
    List<Model> modelList;
    Context context;

    public CustomAdapter(showList shwList, List<Model> modelList) {
        this.shwList = shwList;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(itemView);


        //handle item clicks here
        viewHolder.setOnclickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user click item

                //show data on clicking
                String food = modelList.get(position).getFood();
                String cal = modelList.get(position).getCal();
                Toast.makeText(shwList, food+"\n"+cal, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //this will be called when user long click item

                //Creating alertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(shwList);
                //option to display in dialog
                String[] options = {"Update","Delete"};

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            //update is clicked
                            //get data
                            String id = modelList.get(position).getId();
                            String food = modelList.get(position).getFood();
                            String cal = modelList.get(position).getCal();


                            //intent to start activity
                            Intent intent = new Intent(shwList,addFood.class);
                            //put data in intent
                            intent.putExtra("pId",id);
                            intent.putExtra("pfood",food);
                            intent.putExtra("pcal",cal);
                            //start activity
                            shwList.startActivity(intent);

                        }
                        if (i == 1){
                            //delete is clicked change
                            shwList.deleteData(position);

                        }
                    }
                }).create().show();


            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mFood.setText(modelList.get(position).getFood());
        holder.mCal.setText(modelList.get(position).getCal());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
