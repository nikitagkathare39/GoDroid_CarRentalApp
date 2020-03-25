package com.project.dbmsse.fragment;


/*import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.dbmsse.R;

import java.util.ArrayList;
import java.util.List;


public class BookingFragment extends Fragment {

    private static final String TAG = BookingFragment.class.getSimpleName();

    private RecyclerView bookingRecyclerView;


    public BookingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        bookingRecyclerView = (RecyclerView)view.findViewById(R.id.car_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        bookingRecyclerView.setLayoutManager(gridLayoutManager);
        bookingRecyclerView.setHasFixedSize(true);

        com.project.dbmsse.adapter.ListingAdapter mAdapter = new com.project.dbmsse.adapter.ListingAdapter(getActivity(), getTextData());
        bookingRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private List<com.project.dbmsse.models.CarCategoryObject> getTextData() {

        List<com.project.dbmsse.models.CarCategoryObject> carData = new ArrayList<>();
        carData.add(new com.project.dbmsse.models.CarCategoryObject("", "BMW"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject("", "TOYOTA"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject("", "FORD"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject("", "NISSAN"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject("", "SAAB"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject("", "VOLVO"));

        return carData;
    }

}*/


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.dbmsse.R;

import java.util.ArrayList;
import java.util.List;


public class BookingFragment extends Fragment {

    private static final String TAG = BookingFragment.class.getSimpleName();

    private RecyclerView bookingRecyclerView;


    public BookingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        bookingRecyclerView = (RecyclerView) view.findViewById(R.id.car_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        bookingRecyclerView.setLayoutManager(gridLayoutManager);
        bookingRecyclerView.setHasFixedSize(true);

        com.project.dbmsse.adapter.ListingAdapter mAdapter = new com.project.dbmsse.adapter.ListingAdapter(getActivity(), getTextData());
        bookingRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private List<com.project.dbmsse.models.CarCategoryObject> getTextData() {

        List<com.project.dbmsse.models.CarCategoryObject> carData = new ArrayList<>();
        carData.add(new com.project.dbmsse.models.CarCategoryObject(R.drawable.pic1, "BMW"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject(R.drawable.pic2, "TOYOTA"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject(R.drawable.pic3, "FORD"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject(R.drawable.pic4, "NISSAN"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject(R.drawable.pic5, "SAAB"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject(R.drawable.pic6, "VOLVO"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject(R.drawable.pic6, "VOLVO"));
        carData.add(new com.project.dbmsse.models.CarCategoryObject(R.drawable.pic6, "VOLVO"));


        return carData;
    }
}
