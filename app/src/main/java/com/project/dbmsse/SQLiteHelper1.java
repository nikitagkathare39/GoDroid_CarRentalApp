package com.project.dbmsse;

/*import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {


    static String DATABASE_NAME="CarFeaturesDataBase.db";

    public static final String TABLE_NAME="CarFeatures";

    public static final String Table_Column_ID="id";

    public static final String Table_Column_1_Name="mileage";

    public static final String Table_Column_2_Email="email";

    public static final String Table_Column_3_Password="password";

    public static final String Table_Column_4_Phone = "phone";


    public static final String Table_Column_ID="id";

    public static final String Table_Column_1="mileage";

    public static final String Table_Column_2="fuelType";

    public static final String Table_Column_3="Engine";

    public static final String Table_Column_4 = "NoOfSeats";

    public static final String Table_Column_5 = "fuelEconomy";


    SQLiteDatabase database;

    public SQLiteHelper(Context context) {


        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
       // System.out.println("NIKITA");
       // String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_Name+" VARCHAR, "+Table_Column_2_Email+" VARCHAR, "+Table_Column_3_Password+" VARCHAR,"+Table_Column_4_Phone+" VARCHAR)";
      //  database.execSQL(CREATE_TABLE);

        database.execSQL(
                "create table contacts " +
                        "(id varchar primary key, name varchar,email varchar,password varchar, phone varchar)"
        );
        SignUpActivity s = new SignUpActivity();
        s.InsertDataIntoSQLiteDatabase();

    }
    public void insertdata(String n, String m, String pas, String ph)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table_Column_1_Name, n);
        values.put(Table_Column_2_Email, m);
        values.put(Table_Column_3_Password,pas);
        values.put(Table_Column_4_Phone, ph);



        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(database);

    }

}*/