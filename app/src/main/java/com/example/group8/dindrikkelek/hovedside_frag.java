package com.example.group8.dindrikkelek;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class hovedside_frag extends Fragment implements View.OnClickListener {


    public hovedside_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hovedside, container, false);
        Button spill = view.findViewById(R.id.btn_spill);
        spill.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick (View view){
        switch (view.getId()) {
            case R.id.btn_spill:
            Intent intent = new Intent(getContext(), SpillActivity.class);
            startActivity(intent);
        }

    }

}
