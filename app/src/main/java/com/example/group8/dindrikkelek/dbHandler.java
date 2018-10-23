package com.example.group8.dindrikkelek;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHandler extends SQLiteOpenHelper {

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


    }

    private static void insertLek (SQLiteDatabase db, String navn, String beskrivelse) {
        ContentValues lekValues = new ContentValues();
        lekValues.put("LEKNAVN", navn);
        lekValues.put("BESKRIVELSE", beskrivelse);
        db.insert("LEK", null, lekValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LEK");
        db.execSQL("DROP TABLE IF EXISTS BILDE");
        db.execSQL("DROP TABLE IF EXISTS UTFALL");
        onCreate(db);
    }

}
