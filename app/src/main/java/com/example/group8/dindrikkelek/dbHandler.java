package com.example.group8.dindrikkelek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class dbHandler extends SQLiteOpenHelper {

    private static final String TAG = "dbHandler";
    private static final String DB_NAME = "dinDrikkeLek.db";
    private static final int DB_VERSION = 1;

    dbHandler (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS LEK ("
                + "idLEK_PK INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "LEKNAVN TEXT NOT NULL, "
                + "BESKRIVELSE TEXT NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS BILDE ("
                + "idBILDE_PK INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "FILNAVN TEXT NOT NULL, "
                + "BILDEBESKRIVELSE TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS UTFALL ("
                + "idUTFALL_PK INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "UTFALLTEKST TEXT NOT NULL, "
                + "idLEK_FK INTEGER NOT NULL, "
                + "idBILDE_FK INTEGER, "
                + "FOREIGN KEY (idLEK_FK) REFERENCES LEK (idLEK_PK), "
                + "FOREIGN KEY (idBILDE_FK) REFERENCES BILDE (idBILDE_PK));");

        insertLek(db, "Pekelek", "Pekeleken fungerer slik....");
        insertLek(db, "Shots", "Shots fungerer slik....");
        insertLek(db, "Jeg har aldri", "Jeg har aldri fungerer slik....");
        insertLek(db, "Personen som", "Personen som fungerer slik....");
        insertUtfall(db, "Må ta tre shots", 2);
        insertUtfall(db, "Pek på personen med størst nese", 1);
        insertUtfall(db, "Jeg har aldri brent opp barbidukkene mine", 3);
        insertUtfall(db, "Personen som søler neste gang må spandere drinker", 4);
        insertUtfall(db, "Jeg har aldri spist en edderkopp", 3);
        insertUtfall(db, "Pek på den som er mest drita", 1);


    }

    //hjelpemetode for metoden onCreate()
    private static void insertLek (SQLiteDatabase db, String navn, String beskrivelse) {
        ContentValues lekValues = new ContentValues();
        lekValues.put("LEKNAVN", navn);
        lekValues.put("BESKRIVELSE", beskrivelse);
        db.insert("LEK", null, lekValues);
    }

    private static void insertUtfall (SQLiteDatabase db, String Utfalltekst, int IdLEK_FK){
        ContentValues utfallValues = new ContentValues();
        utfallValues.put("UTFALLTEKST", Utfalltekst);
        utfallValues.put("idLEK_FK", IdLEK_FK);
        db.insert("Utfall", null, utfallValues);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LEK");
        db.execSQL("DROP TABLE IF EXISTS BILDE");
        db.execSQL("DROP TABLE IF EXISTS UTFALL");
        onCreate(db);
    }

    //test
    //metode for å legge til lek gjennom nyTwist
    //inserter i lek bare for å teste og
    // fordi jeg ikke gidder å bry meg om foreign key i UTFALL tabellen
    public boolean addData(String utfalltekst, int idLek) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("UTFALLTEKST", utfalltekst);
        cv.put("idLEK_FK", idLek);

        //en log og db.insert for å legge til rad i tabellen UTFALL
        Log.d(TAG, "AddData: Adding " + utfalltekst + ", " + idLek + " to LEK.");
        long result = db.insert("UTFALL", null, cv);
        db.close();

        //om data ble inserta feil, returnerer den -1/false
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //returnerer liste av leker
    public List<String> getAllLeker(){
        List<String> leker = new ArrayList<>();


        String query = "SELECT * FROM LEK";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //looper gjennom alle rader og legger dem til i lista
        if (cursor.moveToFirst()) {
            do {
                leker.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();
        return leker;

    }

    List<String> lekerFK = new ArrayList<>();



    public List<String> getLekerFK(){
        List<String> lekerFK2 = lekerFK;
        return lekerFK2;

    }

    //returnerer liste av leker FK
    public List<String> setLekerFK(String randomlekID){


       lekerFK.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LEK",
                new String[] {"idLEK_PK", "LEKNAVN"},
                "idLEK_PK = ?",
                new String[]{(randomlekID)}, null, null, null);

        //looper gjennom alle rader og legger dem til i lista
        if (cursor.moveToFirst()) {
            String lekPK = cursor.getString(0);
            String leknavn = cursor.getString(1);
            lekerFK.add(lekPK);
            lekerFK.add(leknavn);

        }


        cursor.close();
        db.close();
        return lekerFK;

    }



    public List<String> getRandomLekPK(){
        List<String> leker = new ArrayList<>();


        String query = "SELECT * FROM LEK";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //looper gjennom alle rader og legger dem til i lista
        if (cursor.moveToFirst()) {
            do {
                leker.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();
        return leker;

    }

/*

    List<String> lekFK = new ArrayList<>();

    public List<String> setLekFK(String randomLek){
        String query = ("SELECT * FROM LEK WHERE LEKNAVN = ?");
        String [] args = new String[] {randomLek};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                lekFK.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return lekFK;


    }


    public List<String> getLekFK() {
        //Utfall.add("heisann");
        List<String> lekFK2 = new ArrayList<>(lekFK);
        return lekFK2;
    }

*/
    List<String> Utfall = new ArrayList<>();

    public List<String> getUTfalltekst() {
        //Utfall.add("heisann");
        List<String> Utfall2 = new ArrayList<>(Utfall);
        return Utfall2;
    }



    public List<String> setUtfallPK(String randomlek) {
        Utfall.clear();
        String query = ("SELECT * FROM Utfall WHERE idLEK_fk= ?");
        String[] arguments= new String[]{randomlek};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, arguments);
        String strengen;
        if (cursor.moveToFirst()) {
            do {
                Utfall.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return Utfall;

    }

    //returnerer data fra LEK og UTFALL
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT UTFALLTEKST\n" +
                "FROM LEK, UTFALL\n" +
                "WHERE idLEK_PK = idLEK_FK;";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void dropUtfall(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS LEK");
        db.execSQL("DROP TABLE IF EXISTS BILDE");
        db.execSQL("DROP TABLE IF EXISTS UTFALL");
        onCreate(db);
    }

}
