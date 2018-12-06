package com.example.group8.dindrikkelek;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group8.dindrikkelek.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class nyttBilde extends Fragment implements View.OnClickListener {
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private ImageView selectedImageView;
    private EditText tittelEditText;
    Spinner spinner;
    Bitmap bitmap;
    Bitmap img;
    byte byteArray[];
    dbHandler Mydbhandler;
    Uri imageUri;
    Context applicationContext = MainActivity.getContextOfApplication();



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
        Button lagre = view.findViewById(R.id.lagre);
        Button tilbake = view.findViewById(R.id.tilbake);
        spinner = view.findViewById(R.id.spinner);
        loadSpinnerData();


        galleri.setOnClickListener(this);
        kamera.setOnClickListener(this);
        lagre.setOnClickListener(this);
        tilbake.setOnClickListener(this);

        return view;

    }



    // metode for å åpne galleriet på telefonen
    public void openGallery () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Velg bilde"), GALLERY_REQUEST_CODE);


    }
/*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        if resultCo
        try {
            bitmap = ((BitmapDrawable) selectedImageView.getDrawable()).getBitmap();
            savedInstanceState.putParcelable("Bitmapimage", bitmap);
        } catch(Exception e) {
            e.getMessage();
        }


    }
    */


    // metode for å åpne kameraet på telefonen
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }

    //tar et image som bitmap og nytt bildeobjekt med tittel
    public void lagreBilde() {
        Bitmap image = ((BitmapDrawable)selectedImageView.getDrawable()).getBitmap();
        try {

            String Utfalltekst = spinner.getSelectedItem().toString();
            String tittel = tittelEditText.getText().toString();

            new dbHandler(getContext()).knyttBildetilUtfall(Utfalltekst, tittel);
            // Toast t = Toast.makeText(getContext(), toasti, Toast.LENGTH_LONG);
            //   t.show();
            boolean test = new dbHandler(getActivity()).addBilde(new Bilde(tittelEditText.getText().toString(), image));
            if(test) {
                String error = getResources().getString(R.string.PicAdded);
                toastMessage(error);
            } else {
                String error = getResources().getString(R.string.PickNotAdded);
                toastMessage(error);
            }
        } catch (Exception e){
            String error = getResources().getString(R.string.ErrorPic);
            Toast t =Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
            t.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode == RESULT_OK && requestCode== GALLERY_REQUEST_CODE ) {

            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                selectedImageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        else if(resultCode == RESULT_OK && requestCode== CAMERA_REQUEST_CODE ) {
            Bitmap img = (Bitmap)data.getExtras().get("data");
            selectedImageView.setImageBitmap(img);
            byte[] photo = Utility.getBytes(img);

        }


    }


    //hjelpemetode for lagreBilde()
    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }



    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.galleri:
                openGallery();
                break;

            case R.id.kamera:
                openCamera();
                break;

            case R.id.lagre:
                lagreBilde();
                break;
            case R.id.tilbake:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new EndreLeggTil()).commit();
        }
    }

    private void loadSpinnerData() {

        dbHandler myDbHandler = new dbHandler(getActivity());

        Cursor data = myDbHandler.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }

        //adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, listData);

        //knytter dataAdapter til spinner
        spinner.setAdapter(dataAdapter);
    }
}
