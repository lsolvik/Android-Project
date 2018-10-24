package com.example.group8.dindrikkelek;

import android.database.Cursor;
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
            Cursor cursor = db.query ("LEK",
                    new String[] {"idLEK_PK", "LEKNAVN", "BESKRIVELSE"},
                    null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String navn = cursor.getString(1);
                String beskrivelse = cursor.getString(2);
                Toast t = Toast.makeText(this, navn + beskrivelse, Toast.LENGTH_LONG);

                t.show();
                cursor.close();
                db.close();
            }

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database ikke tilgjengelig.", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
