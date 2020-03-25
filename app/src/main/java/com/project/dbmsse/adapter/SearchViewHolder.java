package com.project.dbmsse.adapter;


import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dbmsse.R;

public class SearchViewHolder extends RecyclerView.ViewHolder{

    public ImageView carImage;

    public TextView carName;
    public TextView carPrice;
    public TextView carFeature;
    public TextView carDuration;

    public View view;
    public Context mcontext;


    public SearchViewHolder(View itemView) {
        super(itemView);

        carImage = (ImageView)itemView.findViewById(R.id.car_rental_image);

        carName = (TextView)itemView.findViewById(R.id.car_rental_name);
        carPrice = (TextView)itemView.findViewById(R.id.car_rental_price);
        carFeature = (TextView)itemView.findViewById(R.id.car_rental_options);
        carDuration = (TextView)itemView.findViewById(R.id.car_rental_duration);

        view = itemView;
    }
}
