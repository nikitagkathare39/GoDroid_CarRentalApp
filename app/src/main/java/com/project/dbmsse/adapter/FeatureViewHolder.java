package com.project.dbmsse.adapter;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.project.dbmsse.R;

public class FeatureViewHolder extends RecyclerView.ViewHolder{

    public TextView title;
    public TextView detail;

    public FeatureViewHolder(View itemView) {
        super(itemView);

        title = (TextView)itemView.findViewById(R.id.car_feature_title);
        detail = (TextView)itemView.findViewById(R.id.car_price_title);

    }
}
