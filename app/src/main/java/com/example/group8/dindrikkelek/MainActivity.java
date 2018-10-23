package com.example.group8.dindrikkelek;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteOpenHelper dbhandler = new dbHandler(this);
        try {
            SQLiteDatabase db = dbhandler.getWritableDatabase();
            //Code to read/write data from/to the database
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database ikke tilgjengelig.", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
