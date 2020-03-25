package com.project.dbmsse;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.project.dbmsse.customviews.DateBlock;

//import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.project.dbmsse.SQLiteHelper.TABLE_NAME;
import static com.project.dbmsse.SQLiteHelper.TABLE_NAME1;
import static com.project.dbmsse.SQLiteHelper.TABLE_NAME3;
import static com.project.dbmsse.SQLiteHelper.Table_Column_1_Name;
import static com.project.dbmsse.SQLiteHelper.Table_Column_2_Email;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = BookingActivity.class.getSimpleName();

    private ImageView carImage;

    private TextView pickUpLocation, total_amount, dailyp;

    private DateBlock startDateBlock, endDateBlock;

    private String startDay, startMonth, startTime, endDay, endMonth, endTime;

    private CheckBox skiRack, carSeat, navigation;

    private TextView dailyPrice, extraHour, tax, totalAmount, no;

    private TextView name, address, email, phone,dp;

    private RadioButton payPal, creditCard, payNow;
    private int amount;
    String pickuploc, F_Result;

    EditText eText, eText2;
    String newString;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseObj;
    Cursor cursor;
    String SQLiteDataBaseQueryHolder;
    Boolean EditTextEmptyHolder;
    String pickupdate, returndate, paymentMode, noof,bname;
    Date dt1,dt2;
    //long no;
    int noofdays;
    int s1 = 0;
    String s2, s3;
    String val = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        sqLiteHelper = new SQLiteHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Calendar calendar = Calendar.getInstance();
        carImage = (ImageView) findViewById(R.id.car_image);
        //sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();
        //sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        newString = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//        noofdays = Integer.parseInt(findViewById(R.id.noofdays).toString());
        CarActivity c = new CarActivity();
//        c.SQLiteTableinsert();

        String car_name = "TOYOTA";
        //String val;

        pickUpLocation = (TextView) findViewById(R.id.pick_up_address);

        pickuploc = pickUpLocation.getText().toString();

        Button selectpickupDate = findViewById(R.id.pick_up_date);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            bname = extras.getString("brandname");
            //The key argument here must match that used in the other activity
        }
        Log.v("bname","WISHHHHHHHHHHHHHHHH   "+bname);

        //Button returnDate = findViewById(R.id.return_date);

       /* startDateBlock = (DateBlock)findViewById(R.id.pick_up_date);
        startDateBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        endDateBlock = (DateBlock)findViewById(R.id.return_date);
        endDateBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        skiRack = (CheckBox) findViewById(R.id.ski_rack);
        carSeat = (CheckBox) findViewById(R.id.child_car_seat);
        navigation = (CheckBox) findViewById(R.id.navigation_system);

        dailyPrice = (TextView) findViewById(R.id.daily_price);
        extraHour = (TextView) findViewById(R.id.extra_hour);
        tax = (TextView) findViewById(R.id.tax);
        //totalAmount = (TextView) findViewById(R.id.total_amount);

        dailyp = (TextView)findViewById(R.id.dp);

        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + Table_Column_2_Email + "=?", new String[]{newString}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();
                Log.v("Customerdetails","NIKITAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                // If Email is already exists then Result variable value set as Email Found.
                s2 = cursor.getString(cursor.getColumnIndex("name"));
                s3 = cursor.getString(cursor.getColumnIndex("phone"));

                // Closing cursor.
                cursor.close();
            }
        }




        name = (TextView) findViewById(R.id.customer_name);
        name.setText(s2);
        //address = (TextView) findViewById(R.id.customer_address);
        email = (TextView) findViewById(R.id.customer_email);
        email.setText(newString);
        phone = (TextView) findViewById(R.id.customer_phone);
        phone.setText(s3);
        payPal = (RadioButton) findViewById(R.id.pay_with_pay_pal);
        creditCard = (RadioButton) findViewById(R.id.pay_with_credit_card);
        payNow = (RadioButton) findViewById(R.id.pay_later);
        eText = (EditText) findViewById(R.id.editText1);
        //eText2 = (EditText) findViewById(R.id.editText2);
        eText.setInputType(InputType.TYPE_NULL);
//        eText2.setInputType(InputType.TYPE_NULL);
        pickupdate = eText.getText().toString();
  //      returndate = eText2.getText().toString();
    //    Log.v("return date",returndate);
        /*Log.v("substring",returndate.substring(0,1));
        int ret = Integer.parseInt(returndate.substring(0,1));
        int pick = Integer.parseInt(pickupdate.substring(0,1));
        Log.v("ret = ", String.valueOf(ret));
        Log.v("pick = ", String.valueOf(pick));
        int noofdays = ret-pick;*/



        Cursor cursor = sqLiteDatabaseObj.rawQuery("select * from CarFeatures where name='"+ bname +"'",null);
        if (cursor.moveToFirst())
        {
            do
            {
                s1 = cursor.getInt(cursor.getColumnIndex("totalamount"));


            }while (cursor.moveToNext());
        }
        //dailyp = (TextView)findViewById(R.id.dp);
        dailyp.setText(Integer.toString(s1));

        /*sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME2, null, " " + Table_Column_1_Name + "=?", new String[]{bname}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                s1 = cursor.getInt(cursor.getColumnIndex("totalamount"));
                // If Email is already exists then Result variable value set as Email Found.
                //F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }*/
        //amount = s1*noofdays;
     //   totalAmount.setText(s1);
        Log.v("s1","HURRRRRRRRRRRAYYYY"+s1);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.payment_option);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                //textViewChoice.setText("You Selected " + rb.getText());
                paymentMode = (String) rb.getText();
                //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });



        selectpickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                eText.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, 0, 0, 0);
                datePickerDialog.show();
                dt1 = cldr.getTime();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                calendar.add(Calendar.DATE, 7);
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+10000000);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            }
        });

        /*returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                eText2.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, 0, 0, 0);
                datePickerDialog.show();
                dt2 = cldr.getTime();

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                calendar.add(Calendar.DATE, 7);
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+10000000);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            }
        });*/
       /* no = dt2.getTime()-dt1.getTime();


        int noofdays = (int) (no / (1000 * 60 * 60 * 24));

        amount = noofdays*s1;*/

        /*if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("STRING_I_NEED");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }*/
        //Intent intent = getIntent();
        //newString = intent.getStringExtra("STRING_I_NEED");
        //String newString = this.getIntent().getExtras().getString("STRING_I_NEED");


        Log.v("MY EMAIL IS", newString);

        Button payNowButton = (Button) findViewById(R.id.pay_now);
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDataBaseBuild();

                // Creating SQLite table if dose n't exists.
                SQLiteTableBuild();

                // Checking EditText is empty or Not.
                CheckEditTextStatus();
                CheckFinalResult();

                // Method to check Email is already exists or not.
                //CheckingEmailAlreadyExistsOrNot();

                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();


                SQLiteDataBaseBuild1();
                SQLiteTableBuild1();

                SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_NAME3+ " (email,car_name,paid) VALUES('" + newString + "', '" + bname + "', '" + val + "');";
                Log.v("BBBBBBBBB","I GUESS INSERTEDDDDDDDDDD");
                // Executing query.
                sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

                // Closing SQLite database object.
                //sqLiteDatabaseObj.close();



                //Intent checkoutIntent = new Intent(BookingActivity.this, CheckoutActivity.class);
                //startActivity(checkoutIntent);
                Intent i = new Intent(BookingActivity.this, CheckoutActivity.class);
                i.putExtra("brandname",bname);
                startActivity(i);
            }
        });




    }


    public void SQLiteDataBaseBuild() {
        Log.v("SQLiteDatabasebuild", "NIKKKUUUUUUUUUUU");
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteDataBaseBuild1() {
        Log.v("SQLiteDatabasebuild", "NIKKKUUUUUUUUUUU");
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {
        if(paymentMode.equals("Book now and pay later"))
        {
            val = "NO";
        }
        else
        {
            val = "YES";
        }

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, email varchar, pick_up_loc varchar, pick_up_date varchar, return_days integer,amount integer, payment_mode varchar);");
    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase() {
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
        if (EditTextEmptyHolder == true) {


            /*no = Math.abs(dt2.getTime()-dt1.getTime());
            Log.v("noooo = ",String.valueOf(no));

            noofdays = (int) (no / (1000 * 60 * 60 * 24));

            amount = noofdays*s1;*/

            /*String dt1 = eText.getText ().toString ();
            String dt2 = eText2.getText ().toString ();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date1 = simpleDateFormat.parse(dt1);
            Date date2 = simpleDateFormat.parse(dt2);
            long difference = Math.abs(date1.getTime() - date2.getTime());
            int diffInDays = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);*/
            /*(SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

            //long diffInMillies = dt2.getTime() - dt1.getTime();

            //int diffInDays = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            //Log.d("Difference in days", String.valueOf(diffInDays));

            no = (TextView)findViewById(R.id.noofdays);
            noof = no.getText().toString();
            noofdays = Integer.parseInt(noof);

            amount = noofdays*s1;
            //total_amount = (TextView)findViewById(R.id.total_amount);

//            totalAmount.setText(Integer.toString(amount));
                    //Log.d("Difference in days", String.valueOf(diffInDays));
            // SQLite query to insert data into table.
            Log.v("AAAA","Entered INSERTTTTTTTTTTTTTTT");
            //SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_NAME+" (names,email,password,phone) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"','"+PhoneHolder+"');";
            SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_NAME1+ " (email,pick_up_loc,pick_up_date,return_days,amount,payment_mode) VALUES('" + newString + "', '" + pickuploc + "', '" + pickupdate + "', '" + noofdays + "', '" + amount + "', '" + paymentMode + "');";
            Log.v("BBBBBBBBB","I GUESS INSERTEDDDDDDDDDD");
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            //sqLiteDatabaseObj.close();

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

//        eText2.getText().clear();


    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus() {
        Log.v("KATHARE","YOOOOOOOOOHOOOOOODOPDSOFFFFFFFFOOOOOOOOOO");
        String yes = "zero";
        // Getting value from All EditText and storing into String Variables.
        pickuploc = pickUpLocation.getText().toString();
        Log.v("Pick up location",pickuploc);

        pickupdate = eText.getText().toString();
        no = (TextView)findViewById(R.id.noofdays);
        noof = no.getText().toString();
        noofdays = Integer.parseInt(noof);
        Log.v("Pick up date",pickupdate);
//        returndate = eText2.getText().toString();
      //  Log.v("returnDate",returndate);
        //PhoneHolder = phone.getText().toString();
        //Log.v("Nameholder = ",NameHolder);
        //Log.v("EmailHolder = ",EmailHolder);
        if (TextUtils.isEmpty(pickuploc) || TextUtils.isEmpty(pickupdate) || TextUtils.isEmpty(noof)) {

            EditTextEmptyHolder = false;
            yes = "false";
        } else {

            EditTextEmptyHolder = true;
            yes = "true";
            ;
        }
        Log.v("EditTextEmpty = ", yes);
    }

    public void SQLiteTableBuild1() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + SQLiteHelper.Table_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Table_Column_1_Name + " TEXT, " + Table_Column_2_Email + " TEXT, " + Table_Column_3_Password + " TEXT, " + Table_Column_4_Phone + " TEXT);");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + " (pid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, email varchar, car_name varchar, paid varchar);");
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

    }
}
