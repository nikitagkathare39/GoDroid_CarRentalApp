package com.project.dbmsse.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Helper {

    private static final String TAG = Helper.class.getSimpleName();

    private Context context;

    private String pickUpAddress;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    public static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    public Helper(Context context){
        this.context = context;
    }

    public AutocompleteFilter getLocationFilter(){
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        return typeFilter;
    }


    public LatLng getUserLocationFromAddress(String userAddress){
        Geocoder coder = new Geocoder(context);
        LatLng location = null;
        try {
            ArrayList<Address> address = (ArrayList<Address>) coder.getFromLocationName(userAddress, 50);
            for(Address add : address){
                if (add != null) {
                    double longitude = add.getLongitude();
                    double latitude = add.getLatitude();
                    location = new LatLng(latitude, longitude);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    public LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }


    public void refreshMap(GoogleMap mapInstance){
        mapInstance.clear();
    }

    public void getAddressFromLocation(final double lat, final double lon, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> list = geocoder.getFromLocation(lat, lon, 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        // sending back first address line and locality
                        result = address.getAddressLine(0) + ", " + address.getLocality() + ", " +  address.getCountryName() ;
                        Log.d(TAG, "The converted Address " + result);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Impossible to connect to GeoCoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if(result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void displayErrorMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showMessageDialog(Context context, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
