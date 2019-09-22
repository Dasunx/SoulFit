package com.dasun.soulfit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class cUserList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> cuserList;

    public cUserList(Activity context,List<User>cuserList){

        super(context,R.layout.activity_list_layoutc_r, cuserList);
        this.context = context;
        this.cuserList = cuserList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_list_layoutc_r, null,true);

        TextView textViewBirthday = (TextView) listViewItem.findViewById(R.id.l1birthday);
        TextView textViewgender = (TextView) listViewItem.findViewById(R.id.l2gender);
        TextView textViewheight = (TextView) listViewItem.findViewById(R.id.l3height);
        TextView textViewweight = (TextView) listViewItem.findViewById(R.id.l4weight);

        User user = cuserList.get(position);

        textViewBirthday.setText(user.getBirthday());
        textViewgender.setText(user.getGender());
        textViewheight.setText(user.getHeight());
        textViewweight.setText(user.getWeight());

        return listViewItem;
    }
}
