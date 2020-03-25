package com.project.dbmsse.adapter;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.dbmsse.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder{

    public TextView carType;
    public TextView carName;
    public RatingBar ratingBar;
    public TextView price;
    public View view;


    public CategoryViewHolder(View itemView) {
        super(itemView);

        carType = (TextView)itemView.findViewById(R.id.car_type);
        carName = (TextView)itemView.findViewById(R.id.car_name);
        ratingBar = (RatingBar)itemView.findViewById(R.id.rating_bar);
        price = (TextView)itemView.findViewById(R.id.price_per_day);
        view = itemView;
    }
}
