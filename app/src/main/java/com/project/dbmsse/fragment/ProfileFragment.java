package com.project.dbmsse.fragment;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import static com.project.dbmsse.SQLiteHelper.TABLE_NAME;
import com.project.dbmsse.R;
import com.project.dbmsse.SQLiteHelper;
import android.database.sqlite.SQLiteDatabase;

//import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static com.project.dbmsse.SQLiteHelper.TABLE_NAME;
import static com.project.dbmsse.SQLiteHelper.Table_Column_2_Email;


public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private TextView name, email, phone, address;

    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");
        sqLiteHelper = new SQLiteHelper(getActivity());

        sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();
        String em = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // Adding search email query to cursor.
        /*cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + Table_Column_2_Email + "=?", new String[]{em}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                //F_Result = "Email Found";

                String query = "SELECT * FROM Registered_Users WHERE email = " + em;

                Cursor  cursor = sqLiteDatabaseObj.rawQuery(query,null);

                if (cursor != null) {
                    cursor.moveToFirst();
                }
                //return cursor;

                // Closing cursor.
                cursor.close();
            }
        }*/
        //dbHelper = new DBHelper(getApplicationContext());
        //SQLiteDatabase db = dbHelper.getReadableDatabase();
        String s1 = "",s2 = "",s3 = "";
        //sqLiteHelper = new SQLiteHelper(getContext());
        //sqLiteDatabaseObj = SQLiteDatabase.openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabaseObj.rawQuery("select * from Users where email='"+em+"'",null);
        if (cursor.moveToFirst())
        {
            do
            {
                s1 = cursor.getString(cursor.getColumnIndex("name"));
                s2 = cursor.getString(cursor.getColumnIndex("email"));
                s3 = cursor.getString(cursor.getColumnIndex("phone"));


            }while (cursor.moveToNext());
        }
        Log.v("Name issssssss ",s1);

        name = (TextView)view.findViewById(R.id.user_name);
        email = (TextView)view.findViewById(R.id.user_email);
        phone = (TextView)view.findViewById(R.id.user_phone);
        //address = (TextView)view.findViewById(R.id.user_address);
        name.setText(s1);
        email.setText(s2);
        phone.setText(s3);


        return view;
    }

}
