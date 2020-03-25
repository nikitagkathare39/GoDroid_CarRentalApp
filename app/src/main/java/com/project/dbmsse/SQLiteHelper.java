package com.project.dbmsse;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {


    public static String DATABASE_NAME="UserData.db";

    public static final String TABLE_NAME = "Users";

    public static final String TABLE_NAME1 = "BookedCars";

    public static final String TABLE_NAME2 = "CarFeatures";

    public static final String TABLE_NAME3 = "Payment";

    public static final String TABLE_NAME4 = "City";

    public static final String TABLE_NAME5 = "State";

    public static final String Table_Column_ID="id";

    public static final String Table_Column_1_Name="name";

    public static final String Table_Column_2_Email="email";

    public static final String Table_Column_3_Password="password";

    public static final String Table_Column_4_Phone = "phone";

    //public static final String Table_Column_5_="name";

    SQLiteDatabase database;

    public SQLiteHelper(Context context) {


        super(context, DATABASE_NAME, null, 1);
        //database = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
       // System.out.println("NIKITA");
       // String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_Name+" VARCHAR, "+Table_Column_2_Email+" VARCHAR, "+Table_Column_3_Password+" VARCHAR,"+Table_Column_4_Phone+" VARCHAR)";
      //  database.execSQL(CREATE_TABLE);

        database.execSQL(
                "create table if not exists Users(id integer primary key autoincrement, name varchar,email varchar,password varchar, phone varchar, pincode varchar)"
        );
        SignUpActivity s = new SignUpActivity();
        //s.InsertDataIntoSQLiteDatabase();

    }
   /* public static void insertdata(String n, String m, String pas, String ph)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table_Column_1_Name, n);
        values.put(Table_Column_2_Email, m);
        values.put(Table_Column_3_Password,pas);
        values.put(Table_Column_4_Phone, ph);



        db.insert(TABLE_NAME, null, values);
        db.close();
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(database);

    }

}