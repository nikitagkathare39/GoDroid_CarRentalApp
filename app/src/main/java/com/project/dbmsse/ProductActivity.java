package com.project.dbmsse;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = ProductActivity.class.getSimpleName();

    private ImageView carImage;

    private TextView mileage, fuelType, engine, numOfSeats, fuelEconomy, airCondition, hourlyPrice, dailyPrice;

    private ImageView favoriteImage;

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("BMW 5 Series");

        carImage = (ImageView)findViewById(R.id.header_image);

        mileage = (TextView)findViewById(R.id.mileage);
        fuelType = (TextView)findViewById(R.id.fuel_type);
        engine = (TextView)findViewById(R.id.engine);
        numOfSeats = (TextView)findViewById(R.id.num_of_seats);
        fuelEconomy = (TextView)findViewById(R.id.fuel_economy);
        airCondition = (TextView)findViewById(R.id.air_condition);
        hourlyPrice = (TextView)findViewById(R.id.hourly_price);
        dailyPrice = (TextView)findViewById(R.id.daily_price);

        favoriteImage = (ImageView)findViewById(R.id.favorite);

        ratingBar = (RatingBar)findViewById(R.id.rating_bar);



        Button bookingButton = (Button)findViewById(R.id.continue_booking);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingIntent = new Intent(ProductActivity.this, BookingActivity.class);
                startActivity(bookingIntent);
            }
        });

    }

}
