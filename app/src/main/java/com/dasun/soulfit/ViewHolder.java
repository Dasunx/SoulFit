package com.dasun.soulfit;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mFood,mCal;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        //itemClick
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());

            }
        });
        //item long click listner
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view,getAdapterPosition());
                return true;
            }
        });
        //initialize views with model layout.xml file
        mFood = itemView.findViewById(R.id.rFoodTv);
        mCal = itemView.findViewById(R.id.rCalTv);

    }

    private ViewHolder.ClickListener mClickListener;

    //interface for click listener
    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnclickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
