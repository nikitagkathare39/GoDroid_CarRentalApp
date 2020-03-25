package com.project.dbmsse.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreference {

    private SharedPreferences sharedPref;

    public CustomSharedPreference(Context context) {
        sharedPref = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
    }

    public SharedPreferences getInstanceOfSharedPreference(){
        return sharedPref;
    }

    //Save user information
    public void setUserData(String userData){
        sharedPref.edit().putString(Constants.USER_DATA, userData).apply();
    }

    public String getUserData(){
        return sharedPref.getString(Constants.USER_DATA, "");
    }

}
