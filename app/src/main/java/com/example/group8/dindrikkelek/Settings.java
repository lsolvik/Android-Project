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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

public class Settings extends Fragment {

    dbHandler myDbHandler;

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        if (position == 3) {
                            getFragmentManager().beginTransaction().replace(R.id.content_frame,
                                    new hovedside_frag()).commit();
                        }else if (position ==1) {
                            myDbHandler = new dbHandler(getActivity());
                            myDbHandler.deleteAllUtfall();
                            String s = getResources().getString(R.string.ConfirmedDeleteTwists);
                            Toast t = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
                            t.show();


                        }
                        else if (position == 0) {
                            myDbHandler = new dbHandler((getActivity()));
                            myDbHandler.deleteAllBilder();
                            String s = getResources().getString(R.string.ConfirmedDeleteBilder);
                            Toast t = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
                            t.show();

                        } else if(position ==2) {
                            myDbHandler = new dbHandler((getActivity()));
                            myDbHandler.deleteAllLeker();
                            String s = getResources().getString(R.string.ConfirmedDeleteLeker);
                            Toast t = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
                            t.show();
                        }



                    }

        };


        //Add the listener to the list view
        ListView listView = getView().findViewById(R.id.SettingsList);
        listView.setOnItemClickListener(itemClickListener);

        return view;

    }
}
