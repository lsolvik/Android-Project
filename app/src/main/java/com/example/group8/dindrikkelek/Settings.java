package com.example.group8.dindrikkelek;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    dbHandler myDbHandler;

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        contextOfApplication = getApplicationContext();

        //ber activity om å bruke toolbar som sin app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setter icon til drawable
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_hamburger);

        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        if (position == 2) {
                            Intent intent = new Intent(Settings.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }else if (position ==1) {
                            myDbHandler = new dbHandler(getApplicationContext());
                            myDbHandler.deleteAllUtfall();
                            String s = getResources().getString(R.string.ConfirmedDelete);
                            Toast t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                            t.show();


                        }
                        else {


                        }



                    }

        };

//Add the listener to the list view
        ListView listView = (ListView) findViewById(R.id.SettingsList);
        listView.setOnItemClickListener(itemClickListener);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
