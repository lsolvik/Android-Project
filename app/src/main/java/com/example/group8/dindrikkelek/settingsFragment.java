package com.example.group8.dindrikkelek;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.support.constraint.Constraints.TAG;

public class settingsFragment extends Fragment {

    dbHandler myDbHandler;
    ListView settingslist;

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

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


        settingslist = view.findViewById(R.id.SettingsList);
        settingslist.setOnItemClickListener(itemClickListener);
        loadListView();


        return view;

    }
    private void loadListView(){
        Log.d(TAG,"Viser data i listview.");
        String[] listdata;


        listdata = getResources().getStringArray(R.array.Settings);
        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listdata);

    }
}
