package com.project.dbmsse.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;
import com.project.dbmsse.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


public class PlaceAdapter extends ArrayAdapter<PlaceAdapter.PlaceObjects> implements Filterable{

    private static final String TAG = PlaceAdapter.class.getSimpleName();

    private GoogleApiClient googleApiClient;

    private Context context;

    private LatLngBounds bounds;

    private AutocompleteFilter autoCompleteFilter;

    private ArrayList<PlaceObjects> placeList;


    public PlaceAdapter(Context context, GoogleApiClient googleApiClient, LatLngBounds bounds, AutocompleteFilter autoCompleteFilter) {
        super(context, 0);
        this.context = context;
        this.googleApiClient = googleApiClient;
        this.bounds = bounds;
        this.autoCompleteFilter = autoCompleteFilter;

        placeList = new ArrayList<>();
    }

    public void setBounds(LatLngBounds mBounds){
        bounds = mBounds;
    }

    @Override
    public int getCount() {
        return placeList.size();
    }

    @Nullable
    @Override
    public PlaceObjects getItem(int position) {
        return placeList.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlaceObjects placeObjects = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.suggestion_layout, parent, false);
        }
        TextView placeItem = (TextView) convertView.findViewById(R.id.place_id);
        if(placeObjects != null){
            placeItem.setText(placeObjects.toString());
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<PlaceObjects> allPlaces = new ArrayList<>();
                if(constraint != null){
                    allPlaces = getAllPlaces(constraint);
                }
                results.values = allPlaces;
                if(allPlaces != null){
                    results.count = allPlaces.size();
                }else{
                    results.count = 0;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0){
                    placeList = (ArrayList<PlaceObjects>) results.values;
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                if(resultValue instanceof PlaceObjects){
                    return ((PlaceObjects)resultValue).toString();
                }else{
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }

    private ArrayList<PlaceObjects> getAllPlaces(CharSequence constraint){
        if(googleApiClient.isConnected()){
            Log.i(TAG, "Starting autocomplete query for: " + constraint);

            PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions(
                    googleApiClient, constraint.toString(), bounds, autoCompleteFilter);

            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);

            final Status status = autocompletePredictions.getStatus();
            if(!status.isSuccess()){
                Toast.makeText(context, "Error using API", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Display error log " + status.getStatusMessage());
                autocompletePredictions.release();
                return null;
            }
            Log.d(TAG, "Total amount returned " + autocompletePredictions.getCount());
            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList<PlaceObjects> mPlaces = new ArrayList<>(autocompletePredictions.getCount());
            while (iterator.hasNext()){
                AutocompletePrediction mObject =  iterator.next();
                mPlaces.add(new PlaceObjects(mObject.getPlaceId(), mObject.getFullText(null)));
            }
            autocompletePredictions.release();
            return mPlaces;
        }
        Log.e(TAG, "Google API client is not connected");
        return null;
    }

    public class PlaceObjects {
        private CharSequence placeId;
        private CharSequence placeDestination;

        public PlaceObjects(CharSequence placeId, CharSequence placeDestination) {
            this.placeId = placeId;
            this.placeDestination = placeDestination;
        }

        public CharSequence getPlaceId() {
            return placeId;
        }

        public CharSequence getPlaceDestination() {
            return placeDestination;
        }

        @Override
        public String toString(){
            return placeDestination.toString();
        }
    }
}
