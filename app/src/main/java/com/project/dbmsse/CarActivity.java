package com.project.dbmsse;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.dbmsse.adapter.FeatureAdapter;
import com.project.dbmsse.models.FeatureObject;
import com.project.dbmsse.models.SearchObject;

import java.util.ArrayList;
import java.util.List;

import static com.project.dbmsse.DataBaseHandler.TABLE_CARFEATURES;
import static com.project.dbmsse.SQLiteHelper.TABLE_NAME1;
import static com.project.dbmsse.SQLiteHelper.Table_Column_1_Name;
import static com.project.dbmsse.SQLiteHelper.Table_Column_2_Email;

public class CarActivity extends AppCompatActivity {

    SQLiteDatabase SQLITEDATABASE ;
    String brandname;

    private static final String TAG = CarActivity.class.getSimpleName();

    private ImageView carImage;

    private RecyclerView featureRecyclerView, priceRecyclerView;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseObj;
    Cursor cursor;
    String SQLiteDataBaseQueryHolder;
    Boolean EditTextEmptyHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        Log.d("NIKITA","YAYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");

        //DataBaseHandler db = new DataBaseHandler(this);

        sqLiteHelper = new SQLiteHelper(this);

        SQLiteDataBaseBuild();

        // Creating SQLite table if dose n't exists.
        SQLiteTableBuild();

        // Checking EditText is empty or Not.
        //CheckEditTextStatus();
        //CheckFinalResult();

        // Inserting Contacts
        /*Log.d("Insert: ", "Inserting ..Carssssssssssssssssssss");
        db.addCar(new Car("BMW",10,"Diesel","Automatic",10,"No",300,340));
        db.addCar(new Car("TOYOTA",12,"Diesel","Automatic",7,"Yes",180,210));
        db.addCar(new Car("FORD",8,"Petrol","Automatic",5,"Yes",220,240));
        db.addCar(new Car("NISSAN",9,"Diesel","Automatic",8,"Yes",200,230));
        db.addCar(new Car("SAAB",12,"Diesel","Automatic",5,"Yes",180,210));
        db.addCar(new Car("VOLVO",13,"Petrol","Automatic",7,"Yes",260,320));*/
        //db.addContact(new Contact("Tommy", "9522222222"));
        //db.addContact(new Contact("Karthik", "9533333333"));

        Intent intent = getIntent();
         brandname = intent.getStringExtra("brand_name");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle(brandname);

        carImage = (ImageView)findViewById(R.id.selected_car);

        featureRecyclerView = (RecyclerView)findViewById(R.id.car_features);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        featureRecyclerView.setLayoutManager(linearLayoutManager);
        featureRecyclerView.setHasFixedSize(true);

        FeatureAdapter mAdapter = new FeatureAdapter(this, getTestData());
        featureRecyclerView.setAdapter(mAdapter);

        priceRecyclerView = (RecyclerView)findViewById(R.id.car_price);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        priceRecyclerView.setLayoutManager(linear);
        priceRecyclerView.setHasFixedSize(true);

        FeatureAdapter priceAdapter = new FeatureAdapter(this, getTestDataTwo());
        priceRecyclerView.setAdapter(priceAdapter);

        Button bookingButton = (Button)findViewById(R.id.continue_booking);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent bookingIntent = new Intent(CarActivity.this, BookingActivity.class);
                Intent i = new Intent(CarActivity.this, BookingActivity.class);
                i.putExtra("brandname",brandname);
                startActivity(i);
                //startActivity(bookingIntent);
            }
        });
    }

    private List<FeatureObject> getTestData() {
        List<FeatureObject> testdata = new ArrayList<>();
        /*testdata.add(new FeatureObject("Mileage", "23 km"));
        testdata.add(new FeatureObject("Fuel Type", "Petrol"));
        testdata.add(new FeatureObject("Engine", "Automatic"));
        testdata.add(new FeatureObject("Number of Seats", "7 seaters"));
        testdata.add(new FeatureObject("Fuel Economy", "Yes"));*/
        String s1,s2,s3,s4,s5,s6;

        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        /*cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME2, null, " " + Table_Column_1_Name + "=?", new String[]{brandname}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                //F_Result = "Email Found";
                //s1 = cursor.getString(cursor.getColumnIndex("name"));
                s2 = cursor.getString(cursor.getColumnIndex("fueltype"));
                s3 = cursor.getString(cursor.getColumnIndex("engine"));
                s4 = cursor.getString(cursor.getColumnIndex("noofseats"));
                //s5 = cursor.getString(cursor.getColumnIndex("dailyprice"));
                s6 = cursor.getString(cursor.getColumnIndex("fueleconomy"));
                testdata.add(new FeatureObject("Fuel Type", s2));
                testdata.add(new FeatureObject("Engine",s3));
                testdata.add(new FeatureObject("Number of Seats", s4+" seaters"));
                testdata.add(new FeatureObject("Fuel Economy", s6));

                // Closing cursor.
                cursor.close();
            }
        }*/
        Cursor cursor = sqLiteDatabaseObj.rawQuery("select * from CarFeatures where name='"+ brandname +"'",null);
        if (cursor.moveToFirst())
        {
            //do
            //{
                //s1 = cursor.getInt(cursor.getColumnIndex("totalamount"));
                s1 = cursor.getString(cursor.getColumnIndex("name"));
                s2 = cursor.getString(cursor.getColumnIndex("fueltype"));
                s1 = cursor.getString(cursor.getColumnIndex("mileage"));
                s3 = cursor.getString(cursor.getColumnIndex("engine"));
                s4 = cursor.getString(cursor.getColumnIndex("noofseats"));
                //s5 = cursor.getString(cursor.getColumnIndex("dailyprice"));
                s6 = cursor.getString(cursor.getColumnIndex("fueleconomy"));
                testdata.add(new FeatureObject("Fuel Type", s2));
                testdata.add(new FeatureObject("Mileage", s1));
                testdata.add(new FeatureObject("Engine",s3));
                testdata.add(new FeatureObject("Number of Seats", s4+" seaters"));
                testdata.add(new FeatureObject("Fuel Economy", s6));

                // Closing cursor.
                cursor.close();


            //}while (cursor.moveToNext());
        }

        /*Cursor cursor = sqLiteDatabaseObj.rawQuery("select * from CarFeatures",null);
        if (cursor.moveToFirst())
        {
            while(cursor.moveToNext())
            {
                s1 = cursor.getString(cursor.getColumnIndex("name"));
                s2 = cursor.getString(cursor.getColumnIndex("fueltype"));
                s3 = cursor.getString(cursor.getColumnIndex("engine"));
                s4 = cursor.getString(cursor.getColumnIndex("noofseats"));
                s5 = cursor.getString(cursor.getColumnIndex("dailyprice"));
                Log.v("YAY","YAHOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                testdata.add(new FeatureObject("", s1,s5, s3+", "+s4+"-seater, "+s2+", Wifi ", "Daily"));

            }
        }*/
        return testdata;
    }

    private List<FeatureObject> getTestDataTwo() {
        List<FeatureObject> testdata = new ArrayList<>();
        String s1,s2;
        /*testdata.add(new FeatureObject("Daily Price", "$120"));
        testdata.add(new FeatureObject("Total Amount", "$140"));*/
        Cursor cursor = sqLiteDatabaseObj.rawQuery("select * from CarFeatures where name='"+ brandname +"'",null);
        if (cursor.moveToFirst())
        {
            s1 = cursor.getString(cursor.getColumnIndex("dailyprice"));
            s2 = cursor.getString(cursor.getColumnIndex("totalamount"));
            testdata.add(new FeatureObject("Daily Price", s1));
            testdata.add(new FeatureObject("Total Amount", s2));
        }
        return testdata;
    }

    public void SQLiteDataBaseBuild() {
        //Log.v("SQLiteDatabasebuild", "NIKKKUUUUUUUUUUU");
        sqLiteDatabaseObj = openOrCreateDatabase(DataBaseHandler.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CARFEATURES + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, mileage VARCHAR, fueltype VARCHAR, engine VARCHAR, noofseats VARCHAR, fueleconomy VARCHAR, dailyprice INTEGER, totalamount INTEGER);");
    }

    /*public void SQLiteTableinsert()
    {
        Log.d("Insert: ", "Inserting ..Carssssssssssssssssssss");
        db.addCar(new Car("BMW",10,"Diesel","Automatic",10,"No",300,340));
        db.addCar(new Car("TOYOTA",12,"Diesel","Automatic",7,"Yes",180,210));
        db.addCar(new Car("FORD",8,"Petrol","Automatic",5,"Yes",220,240));
        db.addCar(new Car("NISSAN",9,"Diesel","Automatic",8,"Yes",200,230));
        db.addCar(new Car("SAAB",12,"Diesel","Automatic",5,"Yes",180,210));
        db.addCar(new Car("VOLVO",13,"Petrol","Automatic",7,"Yes",260,320));

    }*/

    // Insert data into SQLite database method.
    /*public void InsertDataIntoSQLiteDatabase() {
        //SQLiteHelper.insertdata(NameHolder, EmailHolder, PasswordHolder, PhoneHolder);
        SQLiteDatabase db = SQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table_Column_1_Name, NameHolder);
        values.put(Table_Column_2_Email, EmailHolder);
        values.put(Table_Column_3_Password,PasswordHolder);
        values.put(Table_Column_4_Phone, PhoneHolder);



        db.insert(TABLE_NAME, null, values);
        db.close();

        // If editText is not empty then this block will executed.
        if (EditTextEmptyHolder == true) {

            // SQLite query to insert data into table.
            Log.v("AAAA","Entered INSERTTTTTTTTTTTTTTT");
            //SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_NAME+" (names,email,password,phone) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"','"+PhoneHolder+"');";
            SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_CARFEATURES+ " (email,pick_up_loc,pick_up_date,return_date,amount,payment_mode) VALUES('" + newString + "', '" + pickuploc + "', '" + pickupdate + "', '" + returndate + "', '" + amount + "', '" + paymentMode + "');";
            Log.v("BBBBBBBBB","I GUESS INSERTEDDDDDDDDDD");
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(BookingActivity.this, "Booked Successfully", Toast.LENGTH_LONG).show();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(BookingActivity.this, "Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert() {

        eText.getText().clear();

        eText2.getText().clear();


    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus() {
        Log.v("KATHARE","YOOOOOOOOOHOOOOOODOPDSOFFFFFFFFOOOOOOOOOO");
        String yes = "zero";
        // Getting value from All EditText and storing into String Variables.
        pickuploc = pickUpLocation.getText().toString();
        Log.v("Pick up location",pickuploc);

        pickupdate = eText.getText().toString();
        Log.v("Pick up date",pickupdate);
        returndate = eText2.getText().toString();
        Log.v("returnDate",returndate);
        //PhoneHolder = phone.getText().toString();
        //Log.v("Nameholder = ",NameHolder);
        //Log.v("EmailHolder = ",EmailHolder);
        if (TextUtils.isEmpty(pickuploc) || TextUtils.isEmpty(pickupdate) || TextUtils.isEmpty(returndate)) {

            EditTextEmptyHolder = false;
            yes = "false";
        } else {

            EditTextEmptyHolder = true;
            yes = "true";
            ;
        }
        Log.v("EditTextEmpty = ", yes);
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot() {

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + Table_Column_2_Email + "=?", new String[]{newString}, null, null, null);

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
    public void CheckFinalResult() {

        // Checking whether email is already exists or not.
        // if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            InsertDataIntoSQLiteDatabase();
            //    Toast.makeText(BookingActivity.this,"Car Booked",Toast.LENGTH_LONG).show();

            //}
            //else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            //InsertDataIntoSQLiteDatabase();
            //SQLiteHelper.insertdata(NameHolder, EmailHolder, PasswordHolder, PhoneHolder);

            //}

            //F_Result = "Not_Found" ;

        }

    }*/
}
