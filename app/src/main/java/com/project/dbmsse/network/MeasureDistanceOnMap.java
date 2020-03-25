package com.project.dbmsse.network;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.project.dbmsse.entity.DirectionObject;
import com.project.dbmsse.entity.LegsObject;
import com.project.dbmsse.entity.PolylineObject;
import com.project.dbmsse.entity.RouteObject;
import com.project.dbmsse.entity.StepsObject;
import com.project.dbmsse.utils.CustomApplication;

import java.util.ArrayList;
import java.util.List;

public class MeasureDistanceOnMap {

    private static final String TAG = MeasureDistanceOnMap.class.getSimpleName();

    private Context context;

    public  MeasureDistanceOnMap(Context context){
        this.context = context;
    }

    public void getRentalDistance(String url, GoogleMap mMap, TextView displayDistance){
        GsonRequest<DirectionObject> serverRequest = new GsonRequest<DirectionObject>(
                Request.Method.GET,
                url,
                DirectionObject.class,
                createRequestSuccessListener(mMap, displayDistance),
                createRequestErrorListener());
        ((CustomApplication)context.getApplicationContext()).getNetworkCall().callToRemoteServer(serverRequest);
    }

    private Response.Listener<DirectionObject> createRequestSuccessListener(final GoogleMap mMap, final TextView displayDistance) {
        return new Response.Listener<DirectionObject>() {
            @Override
            public void onResponse(DirectionObject response) {
                try {
                    Log.d("JSON Response", response.toString());
                    if(response.getStatus().equals("OK")){
                        List<LatLng> mDirections = getDirectionPolylines(response.getRoutes(), displayDistance);
                        drawRouteOnMap(mMap, mDirections);
                    }else{
                        Toast.makeText(context, "Server error! try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
    }

    private Response.ErrorListener createRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

    private void drawRouteOnMap(GoogleMap map, List<LatLng> positions){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.RED).geodesic(true);
        options.addAll(positions);
        Polyline polyline = map.addPolyline(options);
    }

    private void setRouteDistanceAndDuration(TextView distanceDuration, String distance, String duration){
        distanceDuration.setText("Distance: " + distance + " Duration: " + duration );
    }

    private List<LatLng> getDirectionPolylines(List<RouteObject> routes, TextView distanceDuration){
        List<LatLng> directionList = new ArrayList<LatLng>();
        for(RouteObject route : routes){
            List<LegsObject> legs = route.getLegs();

            for(LegsObject leg : legs){
                String routeDistance = leg.getDistance().getText();
                String routeDuration = leg.getDuration().getText();
                setRouteDistanceAndDuration(distanceDuration, routeDistance, routeDuration);
                List<StepsObject> steps = leg.getSteps();

                for(StepsObject step : steps){
                    PolylineObject polyline = step.getPolyline();
                    String points = polyline.getPoints();
                    List<LatLng> singlePolyline = decodePoly(points);

                    for (LatLng direction : singlePolyline){
                        directionList.add(direction);
                    }
                }
            }
        }
        return directionList;
    }

    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
