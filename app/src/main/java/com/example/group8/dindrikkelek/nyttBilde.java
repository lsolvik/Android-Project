package com.example.group8.dindrikkelek;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.group8.dindrikkelek.R;

public class nyttBilde extends Fragment implements View.OnClickListener {
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private ImageView selectedImageView;
    private EditText tittelEditText;

    public nyttBilde() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nytt_bilde, container, false);

        this.selectedImageView = view.findViewById(R.id.valgtBilde);
        this.tittelEditText = view.findViewById(R.id.nyttBildeTittel);
        Button galleri = view.findViewById(R.id.galleri);
        Button kamera = view.findViewById(R.id.kamera);

        galleri.setOnClickListener(this);
        kamera.setOnClickListener(this);

        return view;
    }

    // metode for å åpne galleriet på telefonen
    public void openGallery () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Velg bilde"), GALLERY_REQUEST_CODE);
    }

    // metode for å åpne kameraet på telefonen
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.galleri:
                openGallery();
                break;

            case R.id.kamera:
                openCamera();
                break;
        }
    }

}
