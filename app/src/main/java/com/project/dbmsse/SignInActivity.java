package com.project.dbmsse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.dbmsse.models.UserObject;
import com.project.dbmsse.network.GsonRequest;
import com.project.dbmsse.utils.Constants;
import com.project.dbmsse.utils.CustomApplication;
import com.project.dbmsse.utils.Helper;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private FirebaseAuth auth;

    private void userLogin(){
        String em = email.getText().toString().trim();
        String pass  = password.getText().toString().trim();
        Intent i = new Intent(SignInActivity.this, BookingActivity.class);
        String strEmail = email.getText().toString();
        i.putExtra("STRING_I_NEED", strEmail);

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(em)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("signing in Please Wait...");
        progressDialog.show();

        //logging in the user
        auth.signInWithEmailAndPassword(em, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity

                            //SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        /*    SharedPreferences sharedPref = getApplicationContext();
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("email", email.getText().toString());
                            editor.commit();*/

                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else{
                            Toast.makeText(SignInActivity.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private static final String TAG = SignInActivity.class.getSimpleName();

    private EditText email, password;

    private TextView forgottenPassword, signInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar){
            actionBar.hide();
        }

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        forgottenPassword = (TextView)findViewById(R.id.forgotten_password);
        signInLink = (TextView)findViewById(R.id.link_to_registration);

        forgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgottenIntent = new Intent(SignInActivity.this, ForgottenActivity.class);
                startActivity(forgottenIntent);
            }
        });

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(signInIntent);
            }
        });

        Button loginButton = (Button)findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EmailValidator validator = EmailValidator.getInstance();

               // String mEmail = email.getText().toString();
                //String mPassword = password.getText().toString();

                /*if(TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword)){
                    Helper.displayErrorMessage(SignInActivity.this, "Email and password fields must be filled");
                }else if(!validator.isValid(mEmail)){
                        Helper.displayErrorMessage(LoginActivity.this, "You have entered an invalid email");
                }else{
                    if(Helper.isNetworkAvailable(SignInActivity.this)){
                        loginCallToServer(mEmail, mPassword);
                    }else{
                        Helper.displayErrorMessage(SignInActivity.this, "No network available");
                    }
                }*/

               // Intent homeIntent = new Intent(SignInActivity.this, HomeActivity.class);
                //startActivity(homeIntent);
                userLogin();
            }
        });
    }

    private void loginCallToServer(String email, String password){
        Map params = getParams(email, password);
        GsonRequest<UserObject> serverRequest = new GsonRequest<UserObject>(
                Request.Method.POST,
                Constants.PATH_TO_SERVER_LOGIN,
                UserObject.class,
                params,
                createRequestSuccessListener(),
                createRequestErrorListener());

        ((CustomApplication)getApplication()).getNetworkCall().callToRemoteServer(serverRequest);
    }

    private Map getParams(String email, String password){
        Map<String, String> params = new HashMap<String,String>();
        params.put(Constants.EMAIL, email);
        params.put(Constants.PASSWORD, password);
        return params;
    }

    private Response.Listener<UserObject> createRequestSuccessListener() {
        return new Response.Listener<UserObject>() {
            @Override
            public void onResponse(UserObject response) {
                try {
                    if(response != null){
                        //save user login data to a shared preference
                        String userData = ((CustomApplication)getApplication()).getGsonObject().toJson(response);
                        ((CustomApplication)getApplication()).getShared().setUserData(userData);

                        Intent homeIntent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                    } else{
                        Helper.displayErrorMessage(SignInActivity.this, "Login failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

}
