package com.project.dbmsse.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Constants {

    public static final String ID = "id";
    public static final String NAMES = "names";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";

    public static final int MY_SOCKET_TIMEOUT_MS = 5000;

    public static final String USER_DATA = "USER_DATA";
    public static final String SHARED_PREF = "SHARED_PREFERENCE";

    private static final String DIRECTION_API = "https://maps.googleapis.com/maps/api/directions/json?origin=";
    public static final String API_KEY = "AIzaSyAAz9Dip8WqrYcW6Fomm4pCYuLX8v3ps9Y";

    public static final String REMOTE_PATH = "";

    public static final String PUBLIC_FOLDER = "http://192.168.1.162:81/carrental/public/";
    public static final String PUBLIC_API_FOLDER = "http://192.168.1.162:81/carrental/public/api/";

    public static final String PATH_TO_SERVER_LOGIN = PUBLIC_API_FOLDER + "login";
    public static final String PATH_TO_SERVER_SIGN_IN = PUBLIC_API_FOLDER + "signin";
    public static final String PATH_TO_SERVER_RESET_PASSWORD = PUBLIC_API_FOLDER + "passwordreset";


    public static String getUrlSit(String startingAddress, String destinationAddress){
        return DIRECTION_API +startingAddress+"&destination="+destinationAddress+"&key="+API_KEY;
    }

    public static String getUrl(String originLat, String originLon, String destinationLat, String destinationLon){
        return Constants.DIRECTION_API + originLat+","+originLon+"&destination="+destinationLat+","+destinationLon+"&key="+API_KEY;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
