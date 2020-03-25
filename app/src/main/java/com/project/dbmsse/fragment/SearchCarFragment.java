package com.project.dbmsse.fragment;



import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.project.dbmsse.R;
import com.project.dbmsse.SQLiteHelper;
import com.project.dbmsse.adapter.SearchAdapter;
import com.project.dbmsse.models.SearchObject;
import com.project.dbmsse.utils.FullScreenDialog;
import com.project.dbmsse.utils.Helper;

import java.util.ArrayList;
import java.util.List;


public class SearchCarFragment extends Fragment {

    private static final String TAG = SearchCarFragment.class.getSimpleName();

    private LinearLayout filterLayout;
    private LinearLayout sortLayout;

    private RecyclerView carRecyclerView;

    private int selectedPriceAmount;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseObj;
    Cursor cursor;
    //List<SearchObject> data = new ArrayList<>();


    public SearchCarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_car, container, false);

        getActivity().setTitle("Explore Cars");
        sqLiteHelper = new SQLiteHelper(getActivity());
        sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();

        /*filterLayout = (LinearLayout) view.findViewById(R.id.filter_cars);
        filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager ft = getActivity().getSupportFragmentManager();
                FullScreenDialog newFragment = FullScreenDialog.newInstance();
                newFragment.show(ft, "dialog");
            }
        });*/

        /*sortLayout = (LinearLayout)view.findViewById(R.id.sort_cars);
        sortLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSortDialog();
            }
        });*/


        carRecyclerView = (RecyclerView)view.findViewById(R.id.all_cars);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        carRecyclerView.setLayoutManager(linearLayoutManager);
        carRecyclerView.setHasFixedSize(true);

        SearchAdapter sAdapter = new SearchAdapter(getActivity(), getTestData());
        carRecyclerView.setAdapter(sAdapter);

        return view;
    }

    private List<SearchObject> getTestData() {
        List<SearchObject> data = new ArrayList<>();
        /*data.add(new SearchObject("", "BMW", "300 $", "Automatic, 5-seater, Petrol, Wifi", "Daily"));
        data.add(new SearchObject("", "TOYOTA", "180 $", "Automatic, 7-seater, Diesel, Wifi", "Daily,Weekly"));
        data.add(new SearchObject("", "FORD", "220 $", "Automatic, 5-seater, Petrol, Wifi", "Daily"));
        data.add(new SearchObject("", "NISSAN", "280 $", "Automatic, 5-seater, Petrol, Wifi", "Daily,Weekly"));
        data.add(new SearchObject("", "SAAB", "300 $", "Automatic, 7-seater, Diesel, Wifi", "Daily"));
        data.add(new SearchObject("", "VOLVO", "250 $", "Automatic, 5-seater, Petrol, Wifi", "Daily"));*/
        String s1,s2,s3,s4,s5;
        //sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS CarFeatures(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, mileage varchar, fueltype varchar, engine VARCHAR, noofseats varchar, fueleconomy varchar, dailyprice integer, totalamount);");

       Cursor cursor = sqLiteDatabaseObj.rawQuery("select * from CarFeatures",null);
        if (cursor.moveToFirst())
        {
            while(cursor.moveToNext())
            {
                s1 = cursor.getString(cursor.getColumnIndex("name"));
                s2 = cursor.getString(cursor.getColumnIndex("fueltype"));
                s3 = cursor.getString(cursor.getColumnIndex("engine"));
                s4 = cursor.getString(cursor.getColumnIndex("noofseats"));
                s5 = cursor.getString(cursor.getColumnIndex("dailyprice"));
                Log.v("YAY","YAHOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                data.add(new SearchObject("", s1,s5, s3+", "+s4+"-seater, "+s2+", Wifi ", "Daily"));

            }
        }
        return data;
    }


    private void openSortDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.sort_layout, null);

        SeekBar seekBar = (SeekBar)subView.findViewById(R.id.seek_bar_price);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedPriceAmount = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SORT CAR BY DAILY PRICE");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(selectedPriceAmount == 0){
                    Helper.displayErrorMessage(getActivity(), "Nothing is selected");
                }else{
                    Helper.displayErrorMessage(getActivity(), "Price " + selectedPriceAmount);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Helper.displayErrorMessage(getActivity(), "Cancel");
            }
        });

        builder.show();
    }

}
