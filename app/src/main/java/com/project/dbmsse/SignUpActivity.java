package com.project.dbmsse;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
//import com.google.firebase.auth.FirebaseAuth;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;

import static com.project.dbmsse.SQLiteHelper.TABLE_NAME;
import static com.project.dbmsse.SQLiteHelper.TABLE_NAME4;
import static com.project.dbmsse.SQLiteHelper.TABLE_NAME5;
import static com.project.dbmsse.SQLiteHelper.Table_Column_1_Name;
import static com.project.dbmsse.SQLiteHelper.Table_Column_2_Email;
import static com.project.dbmsse.SQLiteHelper.Table_Column_3_Password;
import static com.project.dbmsse.SQLiteHelper.Table_Column_4_Phone;

public class SignUpActivity extends AppCompatActivity {


    public String mNames;
    EditText names, email, password, phone;
    ProgressDialog progressDialog;
    Button reg;
    private FirebaseAuth auth;
    EditText Email, Password, Name, Phone ,locality,city,state,pincode;
    //Button signUpButton = (Button)findViewById(R.id.btnRegister);
    String NameHolder, EmailHolder, PasswordHolder, PhoneHolder, CityHolder, StateHolder, PincodeHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    public void signup(){
        String em=email.getText().toString();
        String pass=password.getText().toString();
        progressDialog.setMessage("signing in Please Wait...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //finish();
                    //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    SQLiteDataBaseBuild();

                    //SQLiteTableBuildtwo();

                    // Creating SQLite table if dose n't exists.
                    SQLiteTableBuild();

                    // Checking EditText is empty or Not.
                    CheckEditTextStatus();

                    // Method to check Email is already exists or not.
                    CheckingEmailAlreadyExistsOrNot();

                    // Empty EditText After done inserting process.
                    EmptyEditTextAfterDataInsert();

                    SQLiteTableBuildone();
                    SQLiteTableBuildtwo();
                    InsertDataIntoSQLiteDatabaseone();
                    InsertDataIntoSQLiteDatabasetwo();


                    Intent intent=new Intent(SignUpActivity.this,BookingActivity.class);
                    intent.putExtra("value", email.getText().toString());

                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this, " Unable to register ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //sqLiteHelper = new SQLiteHelper(this);
        auth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        names = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        locality=(EditText)findViewById(R.id.locality);
        city=(EditText)findViewById(R.id.city);
        state=(EditText)findViewById(R.id.state);
        pincode=(EditText)findViewById(R.id.pincode);
        /*DataBaseHandler db = new DataBaseHandler(this);
        db.addCar(new Car("BMW",10,"Diesel","Automatic",10,"No",300,340));
        db.addCar(new Car("TOYOTA",12,"Diesel","Automatic",7,"Yes",180,210));
        db.addCar(new Car("FORD",8,"Petrol","Automatic",5,"Yes",220,240));
        db.addCar(new Car("NISSAN",9,"Diesel","Automatic",8,"Yes",200,230));
        db.addCar(new Car("SAAB",12,"Diesel","Automatic",5,"Yes",180,210));
        db.addCar(new Car("VOLVO",13,"Petrol","Automatic",7,"Yes",260,320));*/

        sqLiteHelper = new SQLiteHelper(this);
        Button signUpButton = (Button)findViewById(R.id.btnRegister);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
                signup();
            }
        });
        TextView linkToLogin = (TextView)findViewById(R.id.link_to_login);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, BookingActivity.class);
                homeIntent.putExtra("names", names.getText().toString());
                homeIntent.putExtra("Email", email.getText().toString());
                homeIntent.putExtra("phone", phone.getText().toString());
                startActivity(homeIntent);
            }
        });


    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(names)) {
            Toast t = Toast.makeText(this, "You must enter your name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(email)) {
            Toast t = Toast.makeText(this, "You must enter your mail-id to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(password)) {
            password.setError("Password is required!");
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() < 10) {
            Toast.makeText(getApplicationContext(), "10 digits required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(locality)) {
            Toast.makeText(getApplicationContext(), "enter locality", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(city)) {
            Toast.makeText(getApplicationContext(), "enter city", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(state)) {
            Toast.makeText(getApplicationContext(), "enter state", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(pincode)) {
            Toast.makeText(getApplicationContext(), "enter pincode", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void SQLiteDataBaseBuild(){
        Log.v("SQLiteDatabasebuild","Entered finallyyyyy");
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS Users(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, email varchar, password varchar, phone VARCHAR, pincode varchar UNIQUE);");
    }

    public void SQLiteTableBuildone() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS City(pincode varchar PRIMARY KEY NOT NULL, city VARCHAR, FOREIGN KEY(pincode) REFERENCES Users(pincode) );");
    }

    public void SQLiteTableBuildtwo() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS State(city varchar PRIMARY KEY NOT NULL, state VARCHAR, FOREIGN KEY(city) REFERENCES City(city));");
    }

    public void InsertDataIntoSQLiteDatabaseone()
    {
        SQLiteDataBaseQueryHolder = "INSERT INTO " + TABLE_NAME4 + " (pincode,city) VALUES('" + PincodeHolder + "', '" + CityHolder + "');";
        // Executing query.
        sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

        // Closing SQLite database object.
        //sqLiteDatabaseObj.close();
    }
    public void InsertDataIntoSQLiteDatabasetwo()
    {
        SQLiteDataBaseQueryHolder = "INSERT INTO " + TABLE_NAME5 + " (city,state) VALUES('" + CityHolder + "', '" + StateHolder + "');";
        // Executing query.
        sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

        // Closing SQLite database object.
        sqLiteDatabaseObj.close();
    }
    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){
        //SQLiteHelper.insertdata(NameHolder, EmailHolder, PasswordHolder, PhoneHolder);
       /* SQLiteDatabase db = SQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table_Column_1_Name, NameHolder);
        values.put(Table_Column_2_Email, EmailHolder);
        values.put(Table_Column_3_Password,PasswordHolder);
        values.put(Table_Column_4_Phone, PhoneHolder);



        db.insert(TABLE_NAME, null, values);
        db.close();
*/
        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {

            // SQLite query to insert data into table.
            //SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_NAME+" (names,email,password,phone) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"','"+PhoneHolder+"');";
            SQLiteDataBaseQueryHolder = "INSERT INTO " + TABLE_NAME + " (name,email,password,phone,pincode) VALUES('" + NameHolder + "', '" + EmailHolder + "', '" + PasswordHolder + "', '" + PhoneHolder + "' , '" + PincodeHolder + "');";
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            //sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(SignUpActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(SignUpActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        names.getText().clear();

        email.getText().clear();

        password.getText().clear();

        phone.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){
        String yes = "zero";
        // Getting value from All EditText and storing into String Variables.
        NameHolder = names.getText().toString() ;

        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();
        PhoneHolder = phone.getText().toString();
        PincodeHolder = pincode.getText().toString();
        CityHolder = city.getText().toString();
        StateHolder = state.getText().toString();
        Log.v("Nameholder = ",NameHolder);
        Log.v("EmailHolder = ",EmailHolder);
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(PhoneHolder)){

            EditTextEmptyHolder = false ;
            yes = "false";
        }
        else {

            EditTextEmptyHolder = true ;
            yes = "true";;
        }
        Log.v("EditTextEmpty = ",yes);
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }


    // Checking result
    public void CheckFinalResult(){

        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(SignUpActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();
            //SQLiteHelper.insertdata(NameHolder, EmailHolder, PasswordHolder, PhoneHolder);

        }

        F_Result = "Not_Found" ;

    }

}


/*package com.project.dbmsse;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Contacts;
import android.text.TextUtils;
import android.util.Log;
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
//import com.google.firebase.auth.FirebaseAuth;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;

import static com.project.dbmsse.SQLiteHelper.TABLE_NAME;
import static com.project.dbmsse.SQLiteHelper.TABLE_NAME4;
import static com.project.dbmsse.SQLiteHelper.Table_Column_1_Name;
import static com.project.dbmsse.SQLiteHelper.Table_Column_2_Email;
import static com.project.dbmsse.SQLiteHelper.Table_Column_3_Password;
import static com.project.dbmsse.SQLiteHelper.Table_Column_4_Phone;

public class SignUpActivity extends AppCompatActivity {


    public String mNames;
   // PRAGMA foreign_keys = ON;
    EditText names, email, password, phone;
    ProgressDialog progressDialog;
    Button reg;
    int id;
    private FirebaseAuth auth;
    EditText Email, Password, Name, Phone ,locality,city,state,pincode;
    //Button signUpButton = (Button)findViewById(R.id.btnRegister);
    String NameHolder, EmailHolder, PasswordHolder, PhoneHolder, LocalityHolder, CityHolder, StateHolder, PincodeHolder;
    int UIDHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    public void signup(){
        String em=email.getText().toString();
        String pass=password.getText().toString();
        progressDialog.setMessage("signing in Please Wait...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //finish();
                    //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    SQLiteDataBaseBuild();
                    // SQLiteTableBuild1();
                    // Creating SQLite table if dose n't exists.
                    SQLiteTableBuild();
                    SQLiteTableBuild1();
                    // Checking EditText is empty or Not.
                    CheckEditTextStatus();

                    // Method to check Email is already exists or not.
                    CheckingEmailAlreadyExistsOrNot();
                    CheckingEmailUid();
                    InsertDataIntoSQLiteDatabase1();
                    // Empty EditText After done inserting process.
                    EmptyEditTextAfterDataInsert();



                    Intent intent=new Intent(SignUpActivity.this,BookingActivity.class);
                    intent.putExtra("value", email.getText().toString());

                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this, " Unable to register ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //sqLiteHelper = new SQLiteHelper(this);
        auth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        names = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        locality=(EditText)findViewById(R.id.locality);
        city=(EditText)findViewById(R.id.city);
        state=(EditText)findViewById(R.id.state);
        pincode=(EditText)findViewById(R.id.pincode);

        sqLiteHelper = new SQLiteHelper(this);
        Button signUpButton = (Button)findViewById(R.id.btnRegister);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
                signup();
            }
        });
        TextView linkToLogin = (TextView)findViewById(R.id.link_to_login);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                homeIntent.putExtra("names", names.getText().toString());
                homeIntent.putExtra("Email", email.getText().toString());
                homeIntent.putExtra("phone", phone.getText().toString());
                startActivity(homeIntent);
            }
        });

    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(names)) {
            Toast t = Toast.makeText(this, "You must enter your name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(email)) {
            Toast t = Toast.makeText(this, "You must enter your mail-id to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(password)) {
            password.setError("Password is required!");
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() < 10) {
            Toast.makeText(getApplicationContext(), "10 digits required!", Toast.LENGTH_SHORT).show();
            return;


        }
        if (isEmpty(locality)) {
            Toast.makeText(getApplicationContext(), "enter locality", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(city)) {
            Toast.makeText(getApplicationContext(), "enter city", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(state)) {
            Toast.makeText(getApplicationContext(), "enter state", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(pincode)) {
            Toast.makeText(getApplicationContext(), "enter pincode", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void SQLiteDataBaseBuild(){
        Log.v("SQLiteDatabasebuild","Entered finallyyyyy");
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS Registered_Users(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, email varchar, password varchar, phone VARCHAR);");
    }

    public void SQLiteTableBuild1() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("PRAGMA foreign_keys = ON;");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS Address(id INTEGER, locality VARCHAR, city varchar, state varchar, pincode VARCHAR,FOREIGN KEY(id) REFERENCES Registered_Users(id));");
    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){
        //SQLiteHelper.insertdata(NameHolder, EmailHolder, PasswordHolder, PhoneHolder);
       /* SQLiteDatabase db = SQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table_Column_1_Name, NameHolder);
        values.put(Table_Column_2_Email, EmailHolder);
        values.put(Table_Column_3_Password,PasswordHolder);
        values.put(Table_Column_4_Phone, PhoneHolder);



        db.insert(TABLE_NAME, null, values);
        db.close();

        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {

            // SQLite query to insert data into table.
            //SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_NAME+" (names,email,password,phone) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"','"+PhoneHolder+"');";
            SQLiteDataBaseQueryHolder = "INSERT INTO " + TABLE_NAME + " (name,email,password,phone) VALUES('" + NameHolder + "', '" + EmailHolder + "', '" + PasswordHolder + "', '" + PhoneHolder + "');";
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            //sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(SignUpActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(SignUpActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    public void InsertDataIntoSQLiteDatabase1(){
        //CheckingEmailUid();
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        SQLiteDataBaseQueryHolder = "INSERT INTO " + TABLE_NAME4 + "  VALUES('" + UIDHolder + "','" + LocalityHolder + "', '" + CityHolder + "', '" + StateHolder + "', '" + PincodeHolder + "');";
        // Executing query.
        sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
        //sqLiteDatabaseObj.close();

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        names.getText().clear();

        email.getText().clear();

        password.getText().clear();

        phone.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){
        String yes = "zero";
        // Getting value from All EditText and storing into String Variables.
        NameHolder = names.getText().toString() ;
        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();
        PhoneHolder = phone.getText().toString();
        LocalityHolder = locality.getText().toString();
        CityHolder = city.getText().toString();
        StateHolder = state.getText().toString();
        PincodeHolder = pincode.getText().toString();
        Log.v("Nameholder = ",NameHolder);
        Log.v("EmailHolder = ",EmailHolder);
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(PhoneHolder)){

            EditTextEmptyHolder = false ;
            yes = "false";
        }
        else {

            EditTextEmptyHolder = true ;
            yes = "true";;
        }
        Log.v("EditTextEmpty = ",yes);
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                //id = cursor.getInt(cursor.getColumnIndex("id"));
                //UIDHolder = id;
                //Log.v("UIDHOLDER", "UUUUUUUUUUUUU"+UIDHolder);
                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }

    public void CheckingEmailUid(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        //sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();
        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                id = cursor.getInt(cursor.getColumnIndex("id"));
                UIDHolder = id;
                Log.v("UID","UUUUUUUUUUUUUUUUU"+ UIDHolder);
                // If Email is already exists then Result variable value set as Email Found.
                //F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }


    // Checking result
    public void CheckFinalResult(){

        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(SignUpActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            //UIDHolder = id;
            InsertDataIntoSQLiteDatabase();
            //SQLiteHelper.insertdata(NameHolder, EmailHolder, PasswordHolder, PhoneHolder);

        }

        F_Result = "Not_Found" ;

    }

}


*/

/*package com.project.dbmsse;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
//import com.google.firebase.auth.FirebaseAuth;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;

import static com.project.dbmsse.SQLiteHelper.TABLE_NAME;
import static com.project.dbmsse.SQLiteHelper.Table_Column_1_Name;
import static com.project.dbmsse.SQLiteHelper.Table_Column_2_Email;
import static com.project.dbmsse.SQLiteHelper.Table_Column_3_Password;
import static com.project.dbmsse.SQLiteHelper.Table_Column_4_Phone;

public class SignUpActivity extends AppCompatActivity {


    public String mNames;
    EditText names, email, password, phone;
    ProgressDialog progressDialog;
    Button reg;
    private FirebaseAuth auth;
    EditText Email, Password, Name, Phone ;
    //Button signUpButton = (Button)findViewById(R.id.btnRegister);
    String NameHolder, EmailHolder, PasswordHolder, PhoneHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    public void signup(){
        String em=email.getText().toString();
        String pass=password.getText().toString();
        progressDialog.setMessage("signing in Please Wait...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //finish();
                    //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    SQLiteDataBaseBuild();

                    // Creating SQLite table if dose n't exists.
                    SQLiteTableBuild();

                    // Checking EditText is empty or Not.
                    CheckEditTextStatus();

                    // Method to check Email is already exists or not.
                    CheckingEmailAlreadyExistsOrNot();

                    // Empty EditText After done inserting process.
                    EmptyEditTextAfterDataInsert();



                    Intent intent=new Intent(SignUpActivity.this,BookingActivity.class);
                    intent.putExtra("value", email.getText().toString());

                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this, " Unable to register ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //sqLiteHelper = new SQLiteHelper(this);
        auth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        names = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);

        sqLiteHelper = new SQLiteHelper(this);
        Button signUpButton = (Button)findViewById(R.id.btnRegister);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
                signup();
            }
        });
        TextView linkToLogin = (TextView)findViewById(R.id.link_to_login);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                homeIntent.putExtra("names", names.getText().toString());
                homeIntent.putExtra("Email", email.getText().toString());
                homeIntent.putExtra("phone", phone.getText().toString());
                startActivity(homeIntent);
            }
        });

    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(names)) {
            Toast t = Toast.makeText(this, "You must enter your name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(email)) {
            Toast t = Toast.makeText(this, "You must enter your mail-id to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(password)) {password.setError("Password is required!");
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() < 10) {
            Toast.makeText(getApplicationContext(), "10 digits required!", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void SQLiteDataBaseBuild(){
        Log.v("SQLiteDatabasebuild","Entered finallyyyyy");
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS Registered_Users(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, email varchar, password varchar, phone VARCHAR);");
    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){
        //SQLiteHelper.insertdata(NameHolder, EmailHolder, PasswordHolder, PhoneHolder);
        /*SQLiteDatabase db = SQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table_Column_1_Name, NameHolder);
        values.put(Table_Column_2_Email, EmailHolder);
        values.put(Table_Column_3_Password,PasswordHolder);
        values.put(Table_Column_4_Phone, PhoneHolder);



        db.insert(TABLE_NAME, null, values);
        db.close();

        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {

            // SQLite query to insert data into table.
            //SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_NAME+" (names,email,password,phone) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"','"+PhoneHolder+"');";
            SQLiteDataBaseQueryHolder = "INSERT INTO " + TABLE_NAME + " (name,email,password,phone) VALUES('" + NameHolder + "', '" + EmailHolder + "', '" + PasswordHolder + "', '" + PhoneHolder + "');";
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(SignUpActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(SignUpActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        names.getText().clear();

        email.getText().clear();

        password.getText().clear();

        phone.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){
        String yes = "zero";
        // Getting value from All EditText and storing into String Variables.
        NameHolder = names.getText().toString() ;

        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();
        PhoneHolder = phone.getText().toString();
        Log.v("Nameholder = ",NameHolder);
        Log.v("EmailHolder = ",EmailHolder);
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(PhoneHolder)){

            EditTextEmptyHolder = false ;
            yes = "false";
        }
        else {

            EditTextEmptyHolder = true ;
            yes = "true";;
        }
        Log.v("EditTextEmpty = ",yes);
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }


    // Checking result
    public void CheckFinalResult(){

        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(SignUpActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();
            //SQLiteHelper.insertdata(NameHolder, EmailHolder, PasswordHolder, PhoneHolder);

        }

        F_Result = "Not_Found" ;

    }

}

*/


/*import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;

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
//import com.google.firebase.auth.FirebaseAuth;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;

public class SignUpActivity extends AppCompatActivity {


    public String mNames,mEmail, mPhone;
    private EditText names, email, password, phone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        names = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);


        Button signUpButton = (Button)findViewById(R.id.btnRegister);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
            }
        });
        TextView linkToLogin = (TextView)findViewById(R.id.link_to_login);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                homeIntent.putExtra("names", names.getText().toString());
                startActivity(homeIntent);
            }
        });

    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(names)) {
            Toast t = Toast.makeText(this, "You must enter your name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(email)) {
            Toast t = Toast.makeText(this, "You must enter your mail-id to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(password)) {password.setError("Password is required!");
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() < 10) {
            Toast.makeText(getApplicationContext(), "10 digits required!", Toast.LENGTH_SHORT).show();
            return;
        }


    }
}
*/
/*public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    public String mNames;
    private EditText names, email, password, phone;
    //EditText mEdit;
    private ProgressBar progressBar;
    TextView mText;

    //private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     //   mAuth = FirebaseAuth.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar){
            actionBar.hide();
        }

        names = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);

        TextView linkToLogin = (TextView)findViewById(R.id.link_to_login);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                homeIntent.putExtra("names", names.getText().toString());
                startActivity(homeIntent);
            }
        });

        Button signUpButton = (Button)findViewById(R.id.btnRegister);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailValidator validator = EmailValidator.getInstance();

                mNames = names.getText().toString();
                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();
                String mPhone = phone.getText().toString();
                mText = (TextView)findViewById(R.id.profile_name);
                mText.setText(names.getText().toString());
                if (TextUtils.isEmpty(mNames)) {
                    Toast.makeText(getApplicationContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


               if(TextUtils.isEmpty(mNames) || TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(mPhone)){
                    Helper.displayErrorMessage(SignUpActivity.this, "All input fields must be filled");
                }
                else if(!validator.isValid(mEmail)){
                    Helper.displayErrorMessage(SignUpActivity.this, "You have entered an invalid email");
                }else{
                    if(Helper.isNetworkAvailable(SignUpActivity.this)){
                        registerUseToServer(mNames, mEmail, mPassword, mPhone);
                    }else{
                        Helper.displayErrorMessage(SignUpActivity.this, "No network available");
                    }
                }


            }
        });
    }

    private void registerUseToServer(String names, String email, String password, String phone){
        Map params = getParams(names, email, password, phone);
        GsonRequest<UserObject> serverRequest = new GsonRequest<UserObject>(
                Request.Method.POST,
                Constants.PATH_TO_SERVER_SIGN_IN,
                UserObject.class,
                params,
                createRequestSuccessListener(),
                createRequestErrorListener());

        ((CustomApplication)getApplication()).getNetworkCall().callToRemoteServer(serverRequest);
    }

    private Map getParams(String names, String email, String password, String phone){
        Map<String, String> params = new HashMap<String,String>();
        params.put(Constants.NAMES, names);
        params.put(Constants.EMAIL, email);
        params.put(Constants.PASSWORD, password);
        params.put(Constants.PHONE, phone);
        return params;
    }

    private Response.Listener<UserObject> createRequestSuccessListener() {
        return new Response.Listener<UserObject>() {
            @Override
            public void onResponse(UserObject response) {
                try {
                    if(response != null){
                        String userData = ((CustomApplication)getApplication()).getGsonObject().toJson(response);
                        ((CustomApplication)getApplication()).getShared().setUserData(userData);

                        Intent homeIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(homeIntent);

                    }else{
                        Helper.displayErrorMessage(SignUpActivity.this, "Registeration failed. Try again");
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
*/