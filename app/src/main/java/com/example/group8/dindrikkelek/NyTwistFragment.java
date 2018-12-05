package com.example.group8.dindrikkelek;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class NyTwistFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    dbHandler myDbHandler;
    Spinner spinner;
    ListView listview;
    AlertDialog.Builder build;

    public NyTwistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ny_twist, container, false);

        myDbHandler = new dbHandler(getActivity());

        spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();

        final EditText EditText = view.findViewById(R.id.EditText);
        Button btnLagre = view.findViewById(R.id.LagreTwist);

        listview = view.findViewById(R.id.ListView);
        loadListViewData();

        //lytter for knappen i ny_twist fragmentet
        //tar itemposition fra spinner og innhold fra EditText'en
        //og kaller på metoden AddData()
        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utfallet = EditText.getText().toString();

                //vi kan kanskje bruke getSelectedItemPosition
                //fordi leker aldri kommer til å slettes eller endres
                //posisjonen kommer derfor alltid til å være den samme som ID'en til en LEK
                //itemposisjonen blir dermed inserta som foreign key'en i tabellen UTFALL
                int idLek = spinner.getSelectedItemPosition() + 1;

                //sjekker om felt er tomme og tømmer EditText
                if (EditText.length() != 0) {
                    AddData(utfallet, idLek);
                    loadListViewData();
                    EditText.getText().clear();
                } else {
                    toastMessage("Du må legge til noe i tesktfeltet.");
                }
            }
        });

        //Lytter for delete
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {

                //invoke dialogvindu
                build = new AlertDialog.Builder(getActivity());
                build.setTitle("Slett utfall");
                build.setMessage("Bekreft for å slette følgende utfall:\n\n'" + listview.getItemAtPosition(position) + "'");


                //Dialogknapp for sletting
                build.setNegativeButton("Slett", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = new dbHandler(getActivity()).getWritableDatabase();

                        db.execSQL("DELETE FROM UTFALL WHERE UTFALLTEKST = '" + listview.getItemAtPosition(position) + "'");
                        toastMessage("Twist slettet.");
                        db.close();
                        dialog.cancel();
                        loadListViewData();
                    }
                }); //end DELETE

                AlertDialog alert = build.create();
                alert.show();

                return true;
            }
        });//end setOnItemLongClickListener

        return view;

    }

    //metode for å laste inn data fra LEK tabellen til spinneren
    private void loadSpinnerData() {
        myDbHandler = new dbHandler(getActivity());

        //Spinner dropdown elementer
        List<String> leker = myDbHandler.getAllLeker();

        //adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, leker);

        //knytter dataAdapter til spinner
        spinner.setAdapter(dataAdapter);
    }

    //nødvendig metode at klassen skal implementere AdapterView.OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String lek = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Du valgte: " + lek, Toast.LENGTH_SHORT).show();
    }

    //nødvendig metode at klassen skal implementere AdapterView.OnItemSelectedListener
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //ingenting
    }

    //metode som kaller dbHandler og bruker dens metode addData
    public void AddData(String utfallteksten, int idLek) {
        boolean insertData = myDbHandler.addData(utfallteksten, idLek);

        if (insertData) {
            toastMessage("Twist lagt til!");
        } else {
            toastMessage("Noe gikk galt.");
        }
    }

    //hjelpemetode for AddData()
    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void loadListViewData() {
        Log.d(TAG,"Viser data i listview.");

        Cursor data = myDbHandler.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listData);
        listview.setAdapter(adapter);
    }



}
