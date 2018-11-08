package com.example.group8.dindrikkelek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.group8.dindrikkelek.R;
import com.example.group8.dindrikkelek.nyttBilde;

public class EndreLeggTil extends Fragment implements View.OnClickListener {

    public EndreLeggTil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_endre_legg_til, container, false);

        Button btnNyttBilde = view.findViewById(R.id.leggTilBilde);
        Button btnLeggTilTwist = view.findViewById(R.id.leggTilTwist);

        btnNyttBilde.setOnClickListener(this);
        btnLeggTilTwist.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leggTilBilde:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new nyttBilde()).commit();
                break;
            case R.id.leggTilTwist:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new NyTwistFragment()).commit();
                break;
        }
    }
}
