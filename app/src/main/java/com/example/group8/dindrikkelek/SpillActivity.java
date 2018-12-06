package com.example.group8.dindrikkelek;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spill);
        myDbHandler = new dbHandler(this);
        myDbHandler.getAllLeker();
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


    public void onClickShuffle(View view) {

        selectedImageView = view.findViewById(R.id.bildeview);
        selectedImageView.setImageDrawable(null);
        try {
            getLeker();
        } catch (Exception e){
            String error = getResources().getString(R.string.ErrorGame);
            Toast t = Toast.makeText(this, error, Toast.LENGTH_SHORT);
            t.show();
        }

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
        return liste;
    }

    public void getLeker() {
        String lekid = getRandomLekID();
        myDbHandler.setLekerFK(lekid);
        List<String> leker2 = myDbHandler.getLekerFK();;
        getUtfall(leker2.get(0));


    }

    public void getUtfall(String lekID) {
        myDbHandler.setUtfallPK(lekID);
        List<String> Utfallliste = myDbHandler.getUTfalltekst();

        Random rand = new Random();

        for (int counter = 0; counter < Utfallliste.size(); counter++) {
            int randomIndex = rand.nextInt(Utfallliste.size());
            String randomUtfall = Utfallliste.get(randomIndex);
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
            }
        }
    }
}















