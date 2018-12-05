package com.example.group8.dindrikkelek;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Bilde {

    private static final int PREFERRED_WIDTH = 250;
    private static final int PREFERRED_HEIGHT = 250;

    private String bildebeskrivelse;
    private String image;

    public static final int COL_ID = 0;
    public static final int COL_FILNAVN = 1;
    public static final int COL_BILDEBESKRIVELSE = 2;

    public Bilde(Cursor cursor) {
        this.bildebeskrivelse = cursor.getString(COL_BILDEBESKRIVELSE);
        this.image = cursor.getString(COL_FILNAVN);
    }

    //bildeobjektet som lagres
    //tar bildet/image/filnavnet som string
    public Bilde(String bildebeskrivelse, Bitmap image) {
        this.bildebeskrivelse = bildebeskrivelse;
        this.image = bitmapToString(resizeBitmap(image));
    }

    //tar bildet/image/filnavnet som bitmap
    public Bitmap getImage() {
        return stringToBitmap(this.image);
    }

    public String getImageAsString() {
        return this.image;
    }

    public String getBeskrivelse() {
        return this.bildebeskrivelse;
    }

    //konverterer fra bitmap til string og konverterer stringen til et Base64 format
    //isteden for BLOB
    //PNG format, 100% bildekvalitet ved bruk av outputstreamen
    //bruker Base64 library for å encode byte array til en string
    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    //gjør det motsatte. tar en string og konverterer til bytearray ved bruk av Base64 library
    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    //resizer bildet til en foretrukket størrelse
    public Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        //resizedBitmap.recycle();

        return resizedBitmap;
    }
}
