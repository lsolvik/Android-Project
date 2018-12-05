package com.example.group8.dindrikkelek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class nyLekFragment extends Fragment implements View.OnClickListener {

    dbHandler myDbHandler;

    public nyLekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ny_lek, container, false);

        myDbHandler = new dbHandler(getActivity());
        final EditText EditTextNavn = view.findViewById(R.id.EditLekNavn);
        final EditText EditTextBeskrivelse = view.findViewById(R.id.EditLekBeskrivelse);
        Button btnLagre = view.findViewById(R.id.LagreLek);
        Button btnTilbake = view.findViewById(R.id.Tilbake);

        btnLagre.setOnClickListener(this);
        btnTilbake.setOnClickListener(this);

        //lytter for lagreknapp lek
        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String leknavnet = EditTextNavn.getText().toString();
                String beskrivelsen = EditTextBeskrivelse.getText().toString();

                //sjekker om felt er tomme og tømmer EditText
                if (EditTextNavn.length() == 0) {
                    toastMessage("Du må fylle ut begge tekstfelt.");

                } else if (EditTextBeskrivelse.length() == 0) {
                    toastMessage("Du må fylle ut begge tekstfelt.");

                } else {
                    addLek(leknavnet, beskrivelsen);
                    EditTextNavn.getText().clear();
                    EditTextBeskrivelse.getText().clear();
                }

            }
        });

        return view;
    }

    //metode som kaller dbHandler og bruker dens metode addLek
    public void addLek(String leknavnet, String beskrivelsen) {
        boolean insertData = myDbHandler.addLek(leknavnet, beskrivelsen);

        if (insertData) {
            toastMessage("Lek lagt til!");
        } else {
            toastMessage("Noe gikk galt.");
        }
    }

    //hjelpemetode for addLek()
    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Tilbake:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new hovedside_frag()).commit();
        }
    }

}
