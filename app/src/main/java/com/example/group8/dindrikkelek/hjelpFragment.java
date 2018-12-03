package com.example.group8.dindrikkelek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class hjelpFragment extends Fragment {


    public hjelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hjelp, container, false);

        TextView qna = view.findViewById(R.id.txtFaQ);

        qna.setText("Q: Hva er Dindrikkelek?\n" +
                "A: Dindrikkelek er en egendefinert, letthjertet drikkelek rettet mot studenter og " +
                "voksne!\n\n" +
                "Q: Hva slags leker består appen av?\n" +
                "A: Appen består av en pekelek, 'Jeg har aldri..', og 'Personen som..'." +
                "Q: Hva er en Twist og hvordan legger jeg til en Twist?");

        return view;
    }

}
