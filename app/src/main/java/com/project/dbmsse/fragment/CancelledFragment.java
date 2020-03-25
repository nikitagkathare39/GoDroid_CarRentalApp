package com.project.dbmsse.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.dbmsse.R;
import com.project.dbmsse.adapter.ReservationAdapter;
import com.project.dbmsse.models.ReservationObject;

import java.util.ArrayList;
import java.util.List;


public class CancelledFragment extends Fragment {

    private static final String TAG = CancelledFragment.class.getSimpleName();

    private RecyclerView recyclerView;


    public CancelledFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancelled, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.upcoming);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        ReservationAdapter mAdapter = new ReservationAdapter(getActivity(), getTestData());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public List<ReservationObject> getTestData() {
        List<ReservationObject> mObjects = new ArrayList<ReservationObject>();
        mObjects.add(new ReservationObject(1, "Porche Fx", "Vildansvagen 20, 24273 Lund, Sweden", "July 30, 2017", "Time: 8:40 am", "$145", ""));
        mObjects.add(new ReservationObject(1, "Porche Fx", "Vildansvagen 20, 24273 Lund, Sweden", "July 30, 2017", "Time: 8:40 am", "$145", ""));
        mObjects.add(new ReservationObject(1, "Porche Fx", "Vildansvagen 20, 24273 Lund, Sweden", "July 30, 2017", "Time: 8:40 am", "$145", ""));
        mObjects.add(new ReservationObject(1, "Porche Fx", "Vildansvagen 20, 24273 Lund, Sweden", "July 30, 2017", "Time: 8:40 am", "$145", ""));
        return mObjects;
    }

}
