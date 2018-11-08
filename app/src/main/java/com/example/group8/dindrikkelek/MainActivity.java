package com.example.group8.dindrikkelek;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
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

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                new hovedside_frag()).commit();

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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        switch (menuItem.getItemId()) {
            case R.id.nav_endreleker:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new EndreLeggTil()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
