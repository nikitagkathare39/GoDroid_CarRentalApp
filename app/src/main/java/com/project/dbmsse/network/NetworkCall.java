package com.project.dbmsse.network;


import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.project.dbmsse.utils.Constants;

public class NetworkCall {

    private Context context;

    public NetworkCall(Context context){
        this.context = context;
    }

    public void callToRemoteServer(GsonRequest serverRequest){

        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(context).addToRequestQueue(serverRequest);
    }

}
