package com.example.group8.dindrikkelek;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        if (position == 3) {
                            Intent intent = new Intent(Settings.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }else if (position ==1) {
                            myDbHandler = new dbHandler(getApplicationContext());
                            myDbHandler.deleteAllUtfall();
                            String s = getResources().getString(R.string.ConfirmedDeleteTwists);
                            Toast t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                            t.show();


                        }
                        else if (position == 0) {
                            myDbHandler = new dbHandler((getApplicationContext()));
                            myDbHandler.deleteAllBilder();
                            String s = getResources().getString(R.string.ConfirmedDeleteBilder);
                            Toast t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                            t.show();

                        } else if(position ==2) {
                            myDbHandler = new dbHandler((getApplicationContext()));
                            myDbHandler.deleteAllLeker();
                            String s = getResources().getString(R.string.ConfirmedDeleteLeker);
                            Toast t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                            t.show();
                        }



                    }

        };


        //Add the listener to the list view
        ListView listView = (ListView) findViewById(R.id.SettingsList);
        listView.setOnItemClickListener(itemClickListener);


    }

    //Implementing this method adds any items in
    //the menu resource file to the app bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //This method gets called when an
    //action on the app bar is clicked.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        switch (item.getItemId()) {
            case R.id.action_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        final Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_endreleker:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new EndreLeggTil()).commit();
                onDestroy();
                break;
            case R.id.navn_spill:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new hovedside_frag()).commit();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new hjelpFragment()).commit();
                break;
            case R.id.nav_bildegalleri:
                intent = new Intent(this, bildeGalleriActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (f instanceof EndreLeggTil) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (f instanceof NyTwistFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new EndreLeggTil()).commit();

        }  else if (f instanceof nyttBilde) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new EndreLeggTil()).commit();
        }else if (f instanceof nyLekFragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new EndreLeggTil()).commit();

        }else if(f instanceof hjelpFragment) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


        else {
            super.onBackPressed();
        }
    }
}
