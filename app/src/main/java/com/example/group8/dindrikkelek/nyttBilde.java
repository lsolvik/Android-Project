package com.example.group8.dindrikkelek;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.group8.dindrikkelek.R;

import static android.app.Activity.RESULT_OK;

public class nyttBilde extends Fragment implements View.OnClickListener {
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private ImageView selectedImageView;
    private EditText tittelEditText;
    byte byteArray[];
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode == RESULT_OK && requestCode== CAMERA_REQUEST_CODE ) {
            Bitmap bm = (Bitmap)data.getExtras().get("data");
            selectedImageView.setImageBitmap(bm);
            Utility.getBytes(bm);

        }
        else if (resultCode == RESULT_OK && requestCode== GALLERY_REQUEST_CODE){
            imageUri = data.getData();
            selectedImageView.setImageURI(imageUri);
            Bitmap bitmap = ((BitmapDrawable)selectedImageView.getDrawable()).getBitmap();
            byte byteArray[] = Utility.getBytes(bitmap);





        }
    }




public void addEntry(byte[] image) throws SQLException{
    SQLiteOpenHelper dbhandler = new dbHandler(getActivity());
    SQLiteDatabase db = dbhandler.getWritableDatabase();
    
    dbhandler.onCreate(db);
    ContentValues cv = new ContentValues();
    cv.put("idLEK_PK", 1);
    cv.put("FILNAVN", "MORDI");
    cv.put("BILDEBIT", image);
    cv.put("BILDEBESKRIVELSE", "null");
    db.insert("BILDE", null, cv);

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
                addEntry(byteArray);






        }
    }



}
