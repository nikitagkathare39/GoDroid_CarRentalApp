package com.project.dbmsse;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "UserData.db";
    static final String TABLE_CARFEATURES = "CarFeatures";
    private static final String KEY_NAME = "name";
    private static final String KEY_MILEAGE = "mileage";
    private static final String KEY_FUELTYPE = "fueltype";
    private static final String KEY_ENGINE = "engine";
    private static final String KEY_NOOFSEATS = "noofseats";
    private static final String KEY_FUELECONOMY = "fueleconomy";
    private static final String KEY_DAILYPRICE = "dailyprice";
    private static final String KEY_TOTALAMOUNT = "totalamount";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("Dtabasehandler","In DATABASEEEEEEEEEEEEE HANDLERRRRRRRRRRRRR YOHOOOOOOOOOOOOOO");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS CarFeatures " + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_MILEAGE + " INTEGER," + KEY_FUELTYPE + " TEXT,"
                + KEY_ENGINE + " TEXT," + KEY_NOOFSEATS + " INTEGER," + KEY_FUELECONOMY + " TEXT," + KEY_DAILYPRICE + " INTEGER," + KEY_TOTALAMOUNT + " INTEGER" + ")";
        Log.v("DATABASE","after create table");
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARFEATURES);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, car.getName());
        values.put(KEY_MILEAGE, car.getMileage()); // Contact Name
        values.put(KEY_FUELTYPE, car.getFueltype());
        values.put(KEY_ENGINE, car.getEngine());
        values.put(KEY_NOOFSEATS, car.getNoofseats());
        values.put(KEY_FUELECONOMY, car.getFueleconomy());
        values.put(KEY_DAILYPRICE, car.getDailyprice());
        values.put(KEY_TOTALAMOUNT, car.getTotalamount()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CARFEATURES, null, values);
        //2nd argument is String containing nullColumnHack
        //db.close(); // Closing database connection
    }

  /*  // code to get the single contact
    Car getCar(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARFEATURES, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // code to get all contacts in a list view
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
*/
}