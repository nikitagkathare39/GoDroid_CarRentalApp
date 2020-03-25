package com.project.dbmsse.adapter;


/*import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.dbmsse.CarActivity;
import com.project.dbmsse.CarCategoryActivity;
import com.project.dbmsse.R;
import com.project.dbmsse.models.CarCategoryObject;

import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<com.project.dbmsse.adapter.ListingViewHolder>{

    private static final String TAG = ListingAdapter.class.getSimpleName();

    private Context context;

    private List<CarCategoryObject> carList;


    public ListingAdapter(Context context, List<CarCategoryObject> carList) {
        this.context = context;
        this.carList = carList;
    }

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_category_layout, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListingViewHolder holder, int position) {
        CarCategoryObject carCategoryObject = carList.get(position);


        holder.carName.setText(carCategoryObject.getImageName());
        //holder.carImage
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent carCategoryIntent = new Intent(context, CarCategoryActivity.class);
                Intent carCategoryIntent = new Intent(context, CarActivity.class);
                context.startActivity(carCategoryIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }
}
*/

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dbmsse.CarActivity;
import com.project.dbmsse.CarCategoryActivity;
import com.project.dbmsse.R;
import com.project.dbmsse.models.CarCategoryObject;

import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private static final String TAG = ListingAdapter.class.getSimpleName();

    private Context context;
    public String brandname;

    private List<CarCategoryObject> carLists;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView cname;
        public ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.car_category_image);
            cname=view.findViewById(R.id.car_category_name);
        }
    }

    public ListingAdapter(Context context, List<CarCategoryObject> carLists) {
        this.context = context;
        this.carLists = carLists;
    }

    @Override
    public ListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_category_layout, parent, false);
        // return new ListingViewHolder(view);
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View contactview=inflater.inflate(R.layout.car_category_layout,parent,false);
        ViewHolder viewHolder =new ViewHolder(contactview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListingAdapter.ViewHolder holder, int position) {
        final CarCategoryObject carCategoryObject = carLists.get(position);
        ImageView image=holder.imageView;
        TextView text=holder.cname;
        image.setImageResource(carCategoryObject.getId());
        text.setText(carCategoryObject.getImageName());
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                TextView  carname=view.findViewById(R.id.car_category_name);
                brandname=carCategoryObject.getImageName();
                Intent carCategoryIntent = new Intent(context, CarActivity.class);
                carCategoryIntent.putExtra("brand_name",brandname);
                //holder.carImage
                // holder.carImage.setImage(bmw);
                //  holder.view.setOnClickListener(new View.OnClickListener() {
                /// @Override
                //public void onClick(View v) {
                //Intent carCategoryIntent = new Intent(context, CarCategoryActivity.class);
                context.startActivity(carCategoryIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carLists.size();
    }
}