package com.example.group8.dindrikkelek;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SpillActivity extends AppCompatActivity {

    dbHandler myDbHandler;
    ArrayList<String> leker = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spill);
        /* String [] leker = getLeker();
        String lekerstring = leker.toString();
        TextView t = findViewById(R.id.textView);
        t.setText(lekerstring);

*/
    }
/*
    public String[] getLeker(){

            myDbHandler = new dbHandler(this);
            SQLiteDatabase db = myDbHandler.getReadableDatabase();
            Cursor c = db.query("LEK",
                    new String[] {"idLEK_PK", "Leknavn"},
                    null, null, null, null, null);


    }
*/

}
