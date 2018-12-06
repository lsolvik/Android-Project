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
                + "idLEK_PK INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + "LEKNAVN TEXT NOT NULL, "
                + "BESKRIVELSE TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS BILDE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + "FILNAVN TEXT, "
                + "BILDEBESKRIVELSE TEXT)");


        db.execSQL("CREATE TABLE IF NOT EXISTS UTFALL ("
                + "idUTFALL_PK INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + "UTFALLTEKST TEXT NOT NULL, "
                + "idLEK_FK INTEGER NOT NULL, "
                + "idBILDE_FK INTEGER, "
                + "CONSTRAINT fk_idlek FOREIGN KEY (idLEK_FK) REFERENCES LEK (idLEK_PK) ON DELETE CASCADE, "
                + "CONSTRAINT fk_idbilde FOREIGN KEY (idBILDE_FK) REFERENCES BILDE (_id))");

        //testdata. beskrivelsetekst er kun placeholder/eksempler
        insertLek(db, "Pekelek", "Pekeleken fungerer slik....");
        insertLek(db, "Jeg har aldri", "Jeg har aldri fungerer slik....");
        insertLek(db, "Personen som", "Personen som fungerer slik....");
        insertUtfall(db, "Pek på personen med størst nese", 1);
        insertUtfall(db, "Pek på den som er mest drita", 1);
        insertUtfall(db, "Pek på den smarteste i rommet. Han/Hun tar 2 shots!", 1);
        insertUtfall(db, "Pek på den med dårligst antrekk. Det blir 3 slurker på den personen!", 1);
        insertUtfall(db, "Pek på han/hun som er mest edru. Personen må styrte en øl.", 1);
        insertUtfall(db, "Pek på personen som er mest nerdete", 1);
        insertUtfall(db, "Pek på den som du vet har jugd ikveld", 1);
        insertUtfall(db, "Pek på en person du syns ser bra ut ikveld", 1);
        insertUtfall(db, "Pek på den personen som er dårligst kledd!", 1);
        insertUtfall(db, "Jeg har aldri brent opp barbidukkene mine", 2);
        insertUtfall(db, "Jeg har aldri spist ute uten å betale regningen", 2);
        insertUtfall(db, "Jeg har aldri stjålet", 2);
        insertUtfall(db, "Jeg har aldri gått ut av kinosalen fordi filmen var dårlig", 2);
        insertUtfall(db, "Jeg har aldri prøvd å klippe mitt eget hår", 2);
        insertUtfall(db, "Jeg har aldri jukset på en prøve", 2);
        insertUtfall(db, "Jeg har aldri kalt en kvinnelig lærer for mamma", 2);
        insertUtfall(db, "Jeg har aldri blitt stuck i en heis", 2);
        insertUtfall(db, "Jeg har aldri farget håret mitt", 2);
        insertUtfall(db, "Jeg har aldri våknet opp fra en drømm og hatet en person i virkeligheten", 2);
        insertUtfall(db, "Jeg har aldri vært våken i 2 dager på rad", 2);
        insertUtfall(db, "Jeg har aldri grått i offentligheten", 2);
        insertUtfall(db, "Jeg har aldri lest en bok", 2);
        insertUtfall(db, "Jeg har aldri spist en edderkopp", 2);
        insertUtfall(db, "Jeg har aldri spionert på noen", 2);
        insertUtfall(db, "Jeg har aldri brent opp barbidukkene mine", 2);
        insertUtfall(db, "Jeg har aldri blitt kastet ut av en bar", 2);
        insertUtfall(db, "Jeg har aldri brent opp barbidukkene mine", 2);
        insertUtfall(db, "Jeg har aldri hørt på Nickleback", 2);
        insertUtfall(db, "Personen som søler neste gang må spandere drinker", 3);
        insertUtfall(db, "Personen som prater neste gang skal gjøre det godt igjen ved å ta 3 shots!", 3);
        insertUtfall(db, "Personen som har på seg noe rødt plagg skal ta en hand-stand!", 3);
        insertUtfall(db, "Personen som sovner først skal få gjennomgå", 3);



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
    //metode for å legge til utfall gjennom nyTwist
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

    //db metode for å legge til en lek til databasen. metoden blir brukt i nyLekFragment linje 65 og 53
    public boolean addLek(String leknavn, String beskrivelse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("LEKNAVN", leknavn);
        cv.put("BESKRIVELSE", beskrivelse);

        //en log og db.insert for å legge til rad i tabellen LEK
        Log.d(TAG, "AddData: Adding " + leknavn + ", " + beskrivelse + " to LEK.");
        long result = db.insert("LEK", null, cv);
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
    public String getUtfallPK(String utfalltekst){
        SQLiteDatabase db = getWritableDatabase();
        List<String> rows = new ArrayList<>();
        String fk;

        String query = ("SELECT idUTFALL_PK FROM UTFALL WHERE UTFALLTEKST = '" + utfalltekst+"'");
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            rows.add(data.getString(data.getColumnIndex("idUTFALL_PK")));
        }
        db.close();
        fk = rows.get(0);
        return fk;
    }

    public String checkBilde(String UtfallPK){
        SQLiteDatabase db = getWritableDatabase();
        List<String> rows = new ArrayList<>();
        String fk;

        String query = ("SELECT idBILDE_FK FROM UTFALL WHERE idUTFALL_PK = '" + UtfallPK+"'");
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            rows.add(data.getString(data.getColumnIndex("idBILDE_FK")));
        }
        db.close();
        fk = rows.get(0);
        return fk;
    }

    //returnerer data fra LEK og UTFALL
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT UTFALLTEKST\n" +
                "FROM LEK, UTFALL\n" +
                "WHERE idLEK_PK = idLEK_FK;";
        Cursor data = db.rawQuery(query, null);
       // db.close();
        return data;
    }
    //metode for å lese alle bilder. blir brukt i bildeGalleriActivity linje 21
    //for å vise bildene i et gridview
    public Cursor readAllBilder() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(bildeBaseColumns.bildeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null


        );

    }

    //metode som blir brukt i
    public String readBilde(String arg) {
        SQLiteDatabase db = getReadableDatabase();
        List<String> rows = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT FILNAVN FROM BILDE WHERE _id = '"+ arg +"'", null);
        if (c.moveToFirst()) {
            rows.add(c.getString(c.getColumnIndex("FILNAVN")));
        }
        String rader = rows.get(0);
        db.close();
        return rader;
    }


    //metode som tar values fra klassen BaseColumns og klassen Bilde
    //siste value putter filnavnet som en string isteden for bitmap
    public boolean addBilde(Bilde bilde) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //db.insert("UTFALL", null, values);
        values = new ContentValues();
        values.put(bildeBaseColumns.bildeEntry.COLUMN_BILDEBESKRIVELSE, bilde.getBeskrivelse());
        values.put(bildeBaseColumns.bildeEntry.COLUMN_FILNAVN, bilde.getImageAsString());


        return db.insert(bildeBaseColumns.bildeEntry.TABLE_NAME, null, values) != -1;
    }


    public void knyttBildetilUtfall(String utfalltekst, String tittel){
        SQLiteDatabase db = getWritableDatabase();
        String fk;
        List<String> rows = new ArrayList<>();


        String query = ("SELECT _id FROM BILDE WHERE BILDEBESKRIVELSE = '" + tittel+"'");
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            rows.add(data.getString(data.getColumnIndex("_id")));
        }
        fk = rows.get(0);
        query=null;

        ContentValues cv = new ContentValues();
        cv.put("idBILDE_FK", fk);
        db.update("UTFALL", cv, "UTFALLTEKST = '"+utfalltekst +"'", null);
        db.close();

    }


    //sletter alle utfall gjennom navigation drawer "Instillinger" knappen
    public void deleteAllUtfall(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM UTFALL";
        db.execSQL(query);
        db.close();
    }

    //sletter alle bilder gjennom navigation drawer "Instillinger" knappen
    public void deleteAllBilder(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM BILDE";
        db.execSQL(query);
        db.close();

    }

    public void deleteAllLeker() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON");
        String query = "DELETE FROM LEK";
        db.execSQL(query);
        db.close();
    }

}



