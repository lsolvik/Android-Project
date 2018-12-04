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
import android.widget.TextView;
import android.widget.Toast;

import com.example.group8.dindrikkelek.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class nyttBilde extends Fragment implements View.OnClickListener {
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private ImageView selectedImageView;
    private EditText tittelEditText;
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

        galleri.setOnClickListener(this);
        kamera.setOnClickListener(this);
        lagre.setOnClickListener(this);

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
            Bitmap img = (Bitmap)data.getExtras().get("data");


            selectedImageView.setImageBitmap(img);
            byte[] photo = Utility.getBytes(img);
            String beskrivelse = "dsfsdf";
            setBit(img);
            //Mydbhandler.insertImg(photo, beskrivelse);


        }
        else if (resultCode == RESULT_OK && requestCode== GALLERY_REQUEST_CODE){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap)extras.get("data");
            selectedImageView.setImageBitmap(image);

            /*
            imageUri = data.getData();
            selectedImageView.setImageURI(imageUri);
            Bitmap bitmap = ((BitmapDrawable)selectedImageView.getDrawable()).getBitmap();
           // byte[] photo = Utility.getBytes(bitmap);
            */








        }



    }



    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

    public List<Bitmap> setBit(Bitmap bitmap){
        bitmapArray.add(bitmap);
        return bitmapArray;
    }

    public Bitmap getbit(){
        Bitmap b = bitmapArray.get(0);
        return b;
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, "sdfsdf.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    public void addEntry() {


    String filID = Mydbhandler.addBildeRef();
    ImageView v = getView().findViewById(R.id.valgtBilde);
    BitmapDrawable drawable = (BitmapDrawable) v.getDrawable();
    Bitmap bitmapImage = drawable.getBitmap();
    saveToInternalStorage(bitmapImage);

    }

    //tar et image som bitmap og nytt bildeobjekt med tittel
    public void lagreBilde() {
        Bitmap image = ((BitmapDrawable) selectedImageView.getDrawable()).getBitmap();
        new dbHandler(getContext()).addBilde(new Bilde(tittelEditText.getText().toString(), image));
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
        }
    }
}
