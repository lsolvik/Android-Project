package com.example.group8.dindrikkelek;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();


        //ber activity om å bruke toolbar som sin app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setter icon til drawable
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_hamburger);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new hovedside_frag()).commit();
        }

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
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new Settings()).commit();
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
