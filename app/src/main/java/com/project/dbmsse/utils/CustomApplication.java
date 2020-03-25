package com.project.dbmsse.utils;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.dbmsse.models.UserObject;
import com.project.dbmsse.network.NetworkCall;


public class CustomApplication extends Application {

    private Gson gson;
    private GsonBuilder builder;

    private CustomSharedPreference shared;

    private NetworkCall networkCall;

    @Override
    public void onCreate() {
        super.onCreate();
        builder = new GsonBuilder();
        gson = builder.create();
        shared = new CustomSharedPreference(getApplicationContext());
        networkCall = new NetworkCall(getApplicationContext());
    }

    public CustomSharedPreference getShared(){
        return shared;
    }

    public Gson getGsonObject(){
        return gson;
    }

    public NetworkCall getNetworkCall(){
        return networkCall;
    }

    /**
     * get current login user
     * @return
     */
    public UserObject getLoginUser(){
        Gson mGson = getGsonObject();
        String storedUser = getShared().getUserData();
        return mGson.fromJson(storedUser, UserObject.class);
    }
}