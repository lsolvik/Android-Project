package com.example.group8.dindrikkelek;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NyTwistFragment extends Fragment {

    dbHandler myDbHandler;

    public NyTwistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ny_twist, container, false);

        myDbHandler = new dbHandler(getActivity());
        Button btnLagre = view.findViewById(R.id.LagreTwist);
        final EditText EditText = view.findViewById(R.id.EditText);
        final EditText EditText1 = view.findViewById(R.id.EditText1);

        //lytter for knappen i ny_twist fragmentet
        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String navnet = EditText.getText().toString();
                String beskrivelsen = EditText1.getText().toString();

                //sjekker om felt er tomme
                //den tester bare på en av textfeltene og ikke begge?
                if (EditText.length() != 0 || EditText1.length() !=0) {
                    AddData(navnet, beskrivelsen);
                } else {
                    toastMessage("Du må legge til noe i begge tesktfelt.");
                }
            }
        });

        return view;
    }

    //metode som kaller dbHandler og bruker dens metode addData
    public void AddData(String navnet, String beskrivelsen) {
        boolean insertData = myDbHandler.addData(navnet, beskrivelsen);

        if (insertData) {
            toastMessage("Data lagt til!");
        } else {
            toastMessage("Noe gikk galt.");
        }
    }

    //hjelpemetode for metoden ovenfor
    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
