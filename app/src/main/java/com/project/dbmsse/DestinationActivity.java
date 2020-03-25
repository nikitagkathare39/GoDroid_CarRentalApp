package com.project.dbmsse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.dbmsse.adapter.PlaceAdapter;
import com.project.dbmsse.network.MeasureDistanceOnMap;
import com.project.dbmsse.utils.Constants;
import com.project.dbmsse.utils.Helper;

public class DestinationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = DestinationActivity.class.getSimpleName();

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    private TextView startLocation;
    private TextView distanceTravel;

    private String destinationAddress;

    private AutoCompleteTextView destinationLocation;

    private PlaceAdapter mAdapter;

    private MeasureDistanceOnMap distanceOnMap;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        helper = new Helper(DestinationActivity.this);
        distanceOnMap = new MeasureDistanceOnMap(DestinationActivity.this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();
        }
        mAdapter = new PlaceAdapter(DestinationActivity.this, mGoogleApiClient, Helper.BOUNDS_MOUNTAIN_VIEW, helper.getLocationFilter());
        mLocationRequest = helper.createLocationRequest();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startLocation = (TextView)findViewById(R.id.starting_location);

        //get the destination address
        destinationLocation = (AutoCompleteTextView)findViewById(R.id.destination_location);
        destinationLocation.clearFocus();
        destinationLocation.setOnItemClickListener(autoClickListener);

        distanceTravel = (TextView)findViewById(R.id.distance_travel);

        ImageView deleteDestinationAddress = (ImageView)findViewById(R.id.delete_destination);
        deleteDestinationAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationLocation.setText("");
            }
        });

        Button getCostButton = (Button)findViewById(R.id.get_cost);
        getCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String mOrigin = startLocation.getText().toString();
                String mDestination = destinationLocation.getText().toString();

                if(TextUtils.isEmpty(mOrigin) || TextUtils.isEmpty(mDestination)){
                    Toast.makeText(DestinationActivity.this, "Starting and destination addresses must be filled", Toast.LENGTH_LONG).show();
                }else{
                    Intent summaryIntent = new Intent(DestinationActivity.this, RentalActivity.class);
                    summaryIntent.putExtra("STARTING_LOCATION", mOrigin);
                    summaryIntent.putExtra("DESTINATION_LOCATION", mDestination);
                    startActivity(summaryIntent);
                }*/
                Intent reportIntent = new Intent(DestinationActivity.this, RentalActivity.class);
                startActivity(reportIntent);
            }
        });
    }

    private AdapterView.OnItemClickListener autoClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            PlaceAdapter.PlaceObjects selectedAddress = mAdapter.getItem(position);
            assert selectedAddress != null;
            LatLng mLocation = helper.getUserLocationFromAddress(selectedAddress.getPlaceDestination().toString());

            if(mLocation != null){
                helper.refreshMap(mMap);
                //add the starting location marker
                LatLng origin = helper.getUserLocationFromAddress(startLocation.getText().toString());
                if(origin != null){
                    createStartLocationMarker(origin);
                }

                //get and draw line between two location
                String mOrigin = startLocation.getText().toString();
                String mDestination = destinationLocation.getText().toString();
                if(TextUtils.isEmpty(mOrigin) || TextUtils.isEmpty(mDestination)){
                    Toast.makeText(DestinationActivity.this, "Please enter starting and destination addresses", Toast.LENGTH_LONG).show();
                }else {
                    LatLng lOrigin = helper.getUserLocationFromAddress(mOrigin);
                    LatLng lDestination = helper.getUserLocationFromAddress(mDestination);
                    String mUri = Constants.getUrl(String.valueOf(lOrigin.latitude), String.valueOf(lOrigin.longitude), String.valueOf(lDestination.latitude), String.valueOf(lDestination.longitude));
                    distanceOnMap.getRentalDistance(mUri, mMap, distanceTravel);
                }
                //add destination marker
                destinationLocationSelectedFromMap(mLocation);
            }
        }
    };

    private void addCameraToMap(LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(16)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d(TAG, "Connection method has been called");
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    private void destinationLocationSelectedFromMap(LatLng destinationLocation){
        if(destinationLocation != null && mMap != null){
            double latitude = destinationLocation.latitude;
            double longitude = destinationLocation.longitude;
            helper.getAddressFromLocation(latitude, longitude, DestinationActivity.this, new GeoCoderHandler());
            Marker destinationMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
            addCameraToMap(new LatLng(latitude, longitude));
        }
    }

    private void setLocationAddress(){
        if(!TextUtils.isEmpty(destinationAddress)){
            Log.d(TAG, "Log the address value " + destinationAddress);
            destinationLocation.setAdapter(null);
            destinationLocation.setText(destinationAddress);
            destinationLocation.setAdapter(mAdapter);
        }
    }

    private void createStartLocationMarker(LatLng latLng){
        MarkerOptions mOptions = new MarkerOptions().position(latLng);
        mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        addCameraToMap(latLng);
        mMap.addMarker(mOptions);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection has been suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed");
    }

    @Override
    public void onMapClick(LatLng latLng) {
        helper.refreshMap(mMap);
        //Add the starting location marker
        LatLng origin = helper.getUserLocationFromAddress(startLocation.getText().toString());
        if(origin != null){
            createStartLocationMarker(origin);
        }

        //Get and draw line between two location
        String mOrigin = startLocation.getText().toString();
        String mDestination = destinationLocation.getText().toString();

        LatLng lOrigin = helper.getUserLocationFromAddress(mOrigin);
        String mUri = Constants.getUrl(String.valueOf(lOrigin.latitude), String.valueOf(lOrigin.longitude), String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
        distanceOnMap.getRentalDistance(mUri, mMap, distanceTravel);

        //Add destination marker
        destinationLocationSelectedFromMap(latLng);
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @SuppressLint("HandlerLeak")
    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    destinationAddress = bundle.getString("address");
                    setLocationAddress();
                    Log.d(TAG, "Location Result " + destinationAddress);
                    break;
                default:
                    destinationAddress = null;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        //add the starting location marker
        LatLng origin = helper.getUserLocationFromAddress(startLocation.getText().toString());
        if(origin != null){
            createStartLocationMarker(origin);
        }
    }
}
