package com.project.dbmsse;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = CheckoutActivity.class.getSimpleName();

    private ImageView carImage;

    private TextView carName, pickUpAddress, rentalDate, rentalTime, rentalCost, orderNumber,
            contactNumber, rentalStatus;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseObj;
    Cursor cursor;
    String SQLiteDataBaseQueryHolder, rentcost, bname;
    int s1;
    Boolean EditTextEmptyHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();
        String car_name = "TOYOTA";

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            bname = extras.getString("brandname");
            //The key argument here must match that used in the other activity
        }
        Log.v("checkout:","goooooooooooaaaaaa"+bname);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Payment Confirmation");

        carImage = (ImageView)findViewById(R.id.image_path);

        //carName = (TextView)findViewById(R.id.car_name);

        TextView carName = (TextView)findViewById(R.id.car_name);
        carName.setText(bname);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String s2="";

        Cursor cursor = sqLiteDatabaseObj.rawQuery("select * from BookedCars where email='"+ email +"'",null);
        if (cursor.moveToFirst())
        {
            do
            {
                s2 = cursor.getString(cursor.getColumnIndex("pick_up_loc"));


            }while (cursor.moveToNext());
        }

        pickUpAddress = (TextView)findViewById(R.id.car_location);
        pickUpAddress.setText(s2);
        //String rentdate = java.time.LocalDate.now().toString();
        java.util.Date date=new java.util.Date();
        String rentdate = date.toString();
        rentalDate = (TextView)findViewById(R.id.pick_up_date);
        rentalDate.setText(rentdate);
        //rentalTime = (TextView)findViewById(R.id.pick_up_time);



        orderNumber = (TextView)findViewById(R.id.order_number);
        contactNumber = (TextView)findViewById(R.id.contact_number);
        rentalStatus = (TextView)findViewById(R.id.status);

        cursor = sqLiteDatabaseObj.rawQuery("select * from CarFeatures where name='"+ bname +"'",null);
        if (cursor.moveToFirst())
        {
            do
            {
                s1 = cursor.getInt(cursor.getColumnIndex("totalamount"));


            }while (cursor.moveToNext());
        }
        Log.v("s1 in checkout","hdsfgushdbfcusbfvvdsdcfvdufv    "+s1);
        //rentcost = String.valueOf(s1);
        rentalCost = (TextView)findViewById(R.id.rental_price);
        rentalCost.setText(Integer.toString(s1));

        Button backToMenuButton = (Button)findViewById(R.id.back_to_menu);
        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(CheckoutActivity.this, HomeActivity.class);
                startActivity(menuIntent);
            }
        });

    }

}
