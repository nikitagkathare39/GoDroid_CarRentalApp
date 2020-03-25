package com.project.dbmsse.adapter;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dbmsse.R;

public class ListingViewHolder extends RecyclerView.ViewHolder{

    public ImageView carImage;
    public TextView carName;
    public View view;


    public ListingViewHolder(View itemView) {
        super(itemView);
        //convertView.setOnClickListener(this);
        carImage = (ImageView)itemView.findViewById(R.id.car_category_image);
        carName = (TextView)itemView.findViewById(R.id.car_category_name);
        view = itemView;
    }

   /* @Override
    public void onClick(View v) {
        itemListener.recyclerViewListClicked(v, this.getPosition());

    }*/
}