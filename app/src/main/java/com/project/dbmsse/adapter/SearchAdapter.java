package com.project.dbmsse.adapter;


import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.project.dbmsse.R;
import com.project.dbmsse.models.SearchObject;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private static final String TAG = SearchAdapter.class.getSimpleName();

    private Context context;


    private List<SearchObject> searchList;

    public ImageView imgViewIcon;
    //public ImageView imgViewIcon;


    public SearchAdapter(Context context, List<SearchObject> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_cars_layout, parent, false);

        return new SearchViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public static ImageView imgViewIcon;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
           // txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.all_cars);
        }
    }



    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        int resourceId = context.getResources().getIdentifier("shop", "drawable", "com.project.dbmsse");
        SearchObject sObject = searchList.get(position);
       /* Picasso.get().load(sObject.getCarImage()).into(RecyclerView.ViewHolder.imgViewIcon);
        Glide.with(context)
                .load(sObject.getCarImage())
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .into(MyViewHolder.ivfollower);*/
//        Picasso.get().load("drawable/shop.jpg").into(ViewHolder.imgViewIcon);
        holder.carName.setText(sObject.getCarName());
        //holder.imgViewIcon.setImageResource(
        //holder.carImage.setImageDrawable(sObject.getCarImage());
        //holder.carImage.setImageResource();
        holder.carPrice.setText(sObject.getCarPrice());
        holder.carDuration.setText(sObject.getCarDuration());
        holder.carFeature.setText(sObject.getCarFeatures());
        //holder.carImage.setImageDrawable(ContextCompat.getDrawable(holder.mcontext,resourceId));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

  /*  @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_cars_layout, viewGroup, false);
        return new ViewHolder(view);
    }*/


    @Override
    public int getItemCount() {
        return searchList.size();
    }
}
