package com.example.group8.dindrikkelek;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpillActivity extends AppCompatActivity {

    dbHandler myDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spill);
        myDbHandler = new dbHandler(this);
        myDbHandler.getAllLeker();
        //myDbHandler.dropUtfall();
      //  myDbHandler.lagUtfall();
        // View spillc = findViewById(R.id.spill_content);


    }

    // @Override
    public void onClickShuffle(View view) {

        TextView t = findViewById(R.id.text_info);
        t.setText("");
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
        TextView t = findViewById(R.id.text_info);
        //   t.setText(randomLek);
        myDbHandler.setLekerFK(lekid);
        List<String> leker2 = myDbHandler.getLekerFK();
        String output = leker2.toString();
        getUtfall(leker2.get(0));
       // t.setText(output);





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

        }






    }
}















