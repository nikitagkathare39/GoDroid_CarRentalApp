package com.project.dbmsse.fragment;



import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.dbmsse.R;
import com.project.dbmsse.models.UserObject;
import com.project.dbmsse.utils.Helper;


public class BusinessFragment extends Fragment {

    private static final String TAG = BusinessFragment.class.getSimpleName();
    private FirebaseAuth auth;
    private TextView businessName;
    private TextView businessAddress;
    private TextView name;
    private TextView description;
    String newString;
    private TextView email;
    private TextView phone;

    FirebaseDatabase ffd;
    DatabaseReference ddf;

    public BusinessFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        getActivity().setTitle("About Us");
        auth= FirebaseAuth.getInstance();
        ffd=FirebaseDatabase.getInstance();
        ddf=ffd.getReference("table");
        FirebaseUser user=auth.getCurrentUser();
        String gg=user.getUid();
        ddf=ddf.child(gg);


        businessName = (TextView)view.findViewById(R.id.business_name);
        businessAddress = (TextView)view.findViewById(R.id.business_address);
        name = (TextView)view.findViewById(R.id.name);
        description = (TextView)view.findViewById(R.id.description);
        email = (TextView)view.findViewById(R.id.email);
        phone = (TextView)view.findViewById(R.id.phone);
        newString = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Button contactUsButton = (Button)view.findViewById(R.id.contact_us);
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        Button callUsButton = (Button)view.findViewById(R.id.call_us);
        callUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePhoneCall();
            }
        });

        return view;
    }


    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.float_layout, null);

        final EditText mSubject = (EditText)subView.findViewById(R.id.subject);
        final EditText mMessage = (EditText)subView.findViewById(R.id.message);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Contact Us");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subject = mSubject.getText().toString();
                String message = mMessage.getText().toString();
                //FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                // String Uname=user.getDisplayName();
                // DatabaseReference cdf=ddf.child(Uname);
                if(TextUtils.isEmpty(subject) || TextUtils.isEmpty(message)){
                    Helper.displayErrorMessage(getActivity(), "All fields must be filled");
                }
                UserObject uo=new UserObject(subject,message);
                //String uid=ddf.push().getKey();
                //ddf.child(uid).setValue(uo);
                ddf.child("subject").setValue(subject);
                ddf.child("message").setValue(message);
                // send the information to remote server.
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

    private void initiatePhoneCall(){
        Intent callUsIntent = new Intent(Intent.ACTION_CALL);
        callUsIntent.setData(Uri.parse("tel:" + "9110498870"));
        startActivity(callUsIntent);
    }

}














/*package com.project.dbmsse.fragment;



import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.dbmsse.R;
import com.project.dbmsse.models.UserObject;
import com.project.dbmsse.utils.Helper;


public class BusinessFragment extends Fragment {

    private static final String TAG = BusinessFragment.class.getSimpleName();
    private FirebaseAuth auth;
    private TextView businessName;
    private TextView businessAddress;
    private TextView name;
    private TextView description;
    String newString;
    private TextView email;
    private TextView phone;

    FirebaseDatabase ffd;
    DatabaseReference ddf;

    public BusinessFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        getActivity().setTitle("About Us");
        auth= FirebaseAuth.getInstance();
        FirebaseDatabase ffd=FirebaseDatabase.getInstance();
        DatabaseReference ddf=ffd.getReference("Table");
        FirebaseUser user=auth.getCurrentUser();
        String gg=user.getUid();
        ddf=ddf.child(gg);


        businessName = (TextView)view.findViewById(R.id.business_name);
        businessAddress = (TextView)view.findViewById(R.id.business_address);
        name = (TextView)view.findViewById(R.id.name);
        description = (TextView)view.findViewById(R.id.description);
        email = (TextView)view.findViewById(R.id.email);
        phone = (TextView)view.findViewById(R.id.phone);
        newString = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Button contactUsButton = (Button)view.findViewById(R.id.contact_us);
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        Button callUsButton = (Button)view.findViewById(R.id.call_us);
        callUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePhoneCall();
            }
        });

        return view;
    }


    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.float_layout, null);

        final EditText mSubject = (EditText)subView.findViewById(R.id.subject);
        final EditText mMessage = (EditText)subView.findViewById(R.id.message);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Contact Us");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subject = mSubject.getText().toString();
                String message = mMessage.getText().toString();
                //FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                // String Uname=user.getDisplayName();
                // DatabaseReference cdf=ddf.child(Uname);
                if(TextUtils.isEmpty(subject) || TextUtils.isEmpty(message)){
                    Helper.displayErrorMessage(getActivity(), "All fields must be filled");
                }
                UserObject uo=new UserObject(subject,message);
                //String uid=ddf.push().getKey();
                //ddf.child(uid).setValue(uo);
                ddf.child("subject").setValue(subject);
                ddf.child("message").setValue(message);
                // send the information to remote server.
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

    private void initiatePhoneCall(){
        Intent callUsIntent = new Intent(Intent.ACTION_CALL);
        callUsIntent.setData(Uri.parse("tel:" + "0720177156"));
        startActivity(callUsIntent);
    }

}
*/

/*package com.project.dbmsse.fragment;



import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.dbmsse.R;
import com.project.dbmsse.models.UserObject;
import com.project.dbmsse.utils.Helper;


public class BusinessFragment extends Fragment {

    private static final String TAG = BusinessFragment.class.getSimpleName();
    private FirebaseAuth auth;
    private TextView businessName;
    private TextView businessAddress;
    private TextView name;
    private TextView description;
    String newString;
    private TextView email;
    private TextView phone;
    FirebaseDatabase ffd=FirebaseDatabase.getInstance();
    DatabaseReference ddf=ffd.getReference("table");



    public BusinessFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        getActivity().setTitle("About Us");
        auth= FirebaseAuth.getInstance();
        businessName = (TextView)view.findViewById(R.id.business_name);
        businessAddress = (TextView)view.findViewById(R.id.business_address);
        name = (TextView)view.findViewById(R.id.name);
        description = (TextView)view.findViewById(R.id.description);
        email = (TextView)view.findViewById(R.id.email);
        phone = (TextView)view.findViewById(R.id.phone);
        newString = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Button contactUsButton = (Button)view.findViewById(R.id.contact_us);
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        Button callUsButton = (Button)view.findViewById(R.id.call_us);
        callUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePhoneCall();
            }
        });

        return view;
    }


    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.float_layout, null);

        final EditText mSubject = (EditText)subView.findViewById(R.id.subject);
        final EditText mMessage = (EditText)subView.findViewById(R.id.message);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Contact Us");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subject = mSubject.getText().toString();
                String message = mMessage.getText().toString();


                if(TextUtils.isEmpty(subject) || TextUtils.isEmpty(message)){
                    Helper.displayErrorMessage(getActivity(), "All fields must be filled");
                }
                UserObject uo=new UserObject(subject,message);
                Log.v("userobject","CREATEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                String uid=ddf.push().getKey();
                Log.v("string","CREATEEEDDDDDDDDDDDDDDD");
                ddf.child(uid).setValue(uo);
                Log.v("spoorthi","hahaaaaaaaaaaaaaaaaaaaaa");
                // send the information to remote server.
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

    private void initiatePhoneCall(){
        Intent callUsIntent = new Intent(Intent.ACTION_CALL);
        callUsIntent.setData(Uri.parse("tel:" + "0720177156"));
        startActivity(callUsIntent);
    }

}*/

/*package com.project.dbmsse.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;


import com.google.firebase.auth.FirebaseAuth;
import com.project.dbmsse.R;
import com.project.dbmsse.models.UserObject;
import com.project.dbmsse.utils.Helper;




public class BusinessFragment extends Fragment {



    private static final String TAG = BusinessFragment.class.getSimpleName();

    private TextView businessName;
    private TextView businessAddress;
    private TextView name;
    private TextView description;
    private TextView email;
    private TextView phone;
     FirebaseDatabase fd=FirebaseDatabase.getInstance();
     DatabaseReference dr=fd.getReference("user");


    UserObject userObject=new UserObject();

    public BusinessFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        getActivity().setTitle("About Us");

        businessName = (TextView)view.findViewById(R.id.business_name);
        businessAddress = (TextView)view.findViewById(R.id.business_address);
        name = (TextView)view.findViewById(R.id.name);
        description = (TextView)view.findViewById(R.id.description);
        email = (TextView)view.findViewById(R.id.email);
        phone = (TextView)view.findViewById(R.id.phone);
        TextView sub = (TextView) view.findViewById(R.id.subject);
         TextView msg = (TextView)view.findViewById(R.id.message);
       // String em = sub.getText().toString().trim();
       // String message = msg.getText().toString().trim();
       Button contactUsButton = (Button)view.findViewById(R.id.contact_us);
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
               // String em = sub.getText().toString().trim();
                //String message = msg.getText().toString().trim();


               // Toast.makeText(BusinessFragment.this, "success", Toast.LENGTH_LONG).show();
            }

        });

        Button callUsButton = (Button)view.findViewById(R.id.call_us);
        callUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePhoneCall();
            }
        });

        return view;
    }



    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.float_layout, null);

        final EditText mSubject = (EditText)subView.findViewById(R.id.subject);
        final EditText mMessage = (EditText)subView.findViewById(R.id.message);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Contact Us");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subject = mSubject.getText().toString();
                String message = mMessage.getText().toString();


                if(TextUtils.isEmpty(subject) || TextUtils.isEmpty(message)){
                    Helper.displayErrorMessage(getActivity(), "All fields must be filled");
                }
                userObject.setSub(subject);
                userObject.setMsg(message);
                //fd=FirebaseDatabase.getInstance();
                userObject=new UserObject();

                String uid=dr.push().getKey();


                dr.child(uid).setValue(userObject);

                // send the information to remote server.
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

    private void initiatePhoneCall(){
        Intent callUsIntent = new Intent(Intent.ACTION_CALL);
        callUsIntent.setData(Uri.parse("tel:" + "9110498870"));
        startActivity(callUsIntent);
    }

}*/

/*import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.dbmsse.R;
import com.project.dbmsse.utils.Helper;


public class BusinessFragment extends Fragment {

    private static final String TAG = BusinessFragment.class.getSimpleName();

    private TextView businessName;
    private TextView businessAddress;
    private TextView name;
    private TextView description;
    private TextView email;
    private TextView phone;


    public BusinessFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        getActivity().setTitle("About Us");

        businessName = (TextView)view.findViewById(R.id.business_name);
        businessAddress = (TextView)view.findViewById(R.id.business_address);
        name = (TextView)view.findViewById(R.id.name);
        description = (TextView)view.findViewById(R.id.description);
        email = (TextView)view.findViewById(R.id.email);
        phone = (TextView)view.findViewById(R.id.phone);

        Button contactUsButton = (Button)view.findViewById(R.id.contact_us);
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        Button callUsButton = (Button)view.findViewById(R.id.call_us);
        callUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePhoneCall();
            }
        });

        return view;
    }


    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.float_layout, null);

        final EditText mSubject = (EditText)subView.findViewById(R.id.subject);
        final EditText mMessage = (EditText)subView.findViewById(R.id.message);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Contact Us");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subject = mSubject.getText().toString();
                String message = mMessage.getText().toString();

                if(TextUtils.isEmpty(subject) || TextUtils.isEmpty(message)){
                    Helper.displayErrorMessage(getActivity(), "All fields must be filled");
                }
                // send the information to remote server.
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

    private void initiatePhoneCall(){
        Intent callUsIntent = new Intent(Intent.ACTION_CALL);
        callUsIntent.setData(Uri.parse("tel:" + "0720177156"));
        startActivity(callUsIntent);
    }

}*/
