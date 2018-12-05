package com.example.group8.dindrikkelek;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpillActivity extends AppCompatActivity {
    public ImageView selectedImageView;
    public TextView selectedTextView;
    dbHandler myDbHandler;
    Bilde myBilde;
    Bitmap bitmap;
    Bitmap img;
    String utfall;
    Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spill);
        myDbHandler = new dbHandler(this);
        myDbHandler.getAllLeker();
        //myDbHandler.dropUtfall();
        //  myDbHandler.lagUtfall();
        // View spillc = findViewById(R.id.spill_content);
        if (savedInstanceState != null) {
            img = savedInstanceState.getParcelable("Bitmapimage");
            selectedImageView = findViewById(R.id.bildeview);
            selectedTextView = findViewById(R.id.text_utfall);
            selectedImageView.setImageBitmap(img);
            String hei = savedInstanceState.getString("utfall");
            selectedTextView.setText(hei);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        selectedTextView = findViewById(R.id.text_utfall);
        utfall = selectedTextView.getText().toString();
        try {
            bitmap = ((BitmapDrawable) selectedImageView.getDrawable()).getBitmap();
            savedInstanceState.putParcelable("Bitmapimage", bitmap);
        } catch(Exception e) {
            e.getMessage();
        }
        savedInstanceState.putString("utfall", utfall);

    }

    // @Override
    public void onClickShuffle(View view) {

      //  TextView t = findViewById(R.id.text_info);
      //  myDbHandler.dropUtfall();
        //t.setText("");
        selectedImageView = view.findViewById(R.id.bildeview);
        selectedImageView.setImageDrawable(null);
        getLeker();





    }

    public String getRandomLekID(){
        List<String> leker = myDbHandler.getRandomLekPK();
        Random rand = new Random();
        String liste = leker.toString();

        for (int counter = 0; counter < leker.size(); counter++) {
            int randomIndex = rand.nextInt(leker.size());
            String random = leker.get(randomIndex);
            return random;



        }
        String randomID = leker.get(0);
        return liste;
    }

    public void getLeker() {
        String lekid = getRandomLekID();
        //TextView t = findViewById(R.id.text_info);
        //   t.setText(randomLek);
        myDbHandler.setLekerFK(lekid);
        List<String> leker2 = myDbHandler.getLekerFK();
        String output = leker2.toString();
        getUtfall(leker2.get(0));
        //t.setText(output);





    }

    public void getUtfall(String lekID) {
        myDbHandler.setUtfallPK(lekID);
        List<String> Utfallliste = myDbHandler.getUTfalltekst();

        Random rand = new Random();
        //String heisann = pk.toString();
        String liste = Utfallliste.toString();

        for (int counter = 0; counter < Utfallliste.size(); counter++) {
            int randomIndex = rand.nextInt(Utfallliste.size());
            String randomUtfall = Utfallliste.get(randomIndex);
            String gammelverdi = randomUtfall;
            TextView UtfallTekst = findViewById(R.id.text_utfall);
            UtfallTekst.setText(randomUtfall);
            String UtfallPK = myDbHandler.getUtfallPK(randomUtfall);
            String bilde = myDbHandler.checkBilde(UtfallPK);

            if (bilde != null) {
                try {
                    String bildestring = myDbHandler.readBilde(bilde);
                    selectedImageView = findViewById(R.id.bildeview);
                    Bitmap encodeByte = myBilde.stringToBitmap(bildestring);
                    selectedImageView.setImageBitmap(encodeByte);
                } catch (Exception e) {
                    e.getMessage();

                }

                break;
            } else if (bilde == null) {
                Toast t = Toast.makeText(this, "Denne har IKKE bilde", Toast.LENGTH_SHORT);
                t.show();
                break;
            }


        }






    }
}















