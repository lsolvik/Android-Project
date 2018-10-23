package com.example.group8.dindrikkelek;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHandler extends SQLiteOpenHelper {

    //versjon
    private static final int DB_VERSION = 1;

    //databasenavnet
    private static final String DB_NAME = "dinDrikkeLekDB.db";

    //tabellnavn
    public static final String TBL_LEK = "lek";
    public static final String TBL_UTFALL = "utfall";
    public static final String TBL_BILDE = "bilde";

    //LEK tabell kolonnenavn
    private static final String KEY_ID_LEK_PK = "idLek_PK";
    private static final String KEY_LEKNAVN = "lekNavn";
    // private static final String KEY_UTFALL_FK = "utfall_FK";
    private static final String KEY_LEK_BESKRIVELSE = "lekBeskrivelse";

    //UTFALL tabell kolonnenavn
    private static final String KEY_ID_UTFALL_PK = "idUtfall_PK";
    private static final String KEY_UTFALLTEKST = "utfallTekst";
    private static final String KEY_ID_LEK_FK = "idLek_FK";
    private static final String KEY_ID_BILDE_FK = "idBilde_FK";

    //BILDE tabell kolonnenavn
    private static final String KEY_ID_BILDE_PK = "idBilde_PK";
    private static final String KEY_FILNAVN = "filnavn";
    private static final String KEY_BILDE_BESKRIVELSE = "bildeBeskrivelse";

    //LEK tabell create
    private static final String LEK_TABLE_CREATE = "create table "
            + TBL_LEK + " ("
            + KEY_ID_LEK_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_LEKNAVN + " TEXT NOT NULL, "
            + KEY_LEK_BESKRIVELSE + " TEXT NOT NULL" + ")";

    //BILDE tabell create
    private static final String BILDE_TABLE_CREATE = "create table "
            + TBL_BILDE + " ("
            + KEY_ID_BILDE_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_FILNAVN + " TEXT NOT NULL, "
            + KEY_BILDE_BESKRIVELSE + " TEXT NOT NULL" + ")";

    //UTFALL tabell create
    private static final String UTFALL_TABLE_CREATE = "create table "
            + TBL_UTFALL + " ("
            + KEY_ID_UTFALL_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_UTFALLTEKST + " TEXT NOT NULL, "
            + KEY_ID_LEK_FK + " INTEGER NOT NULL, "
            + KEY_ID_BILDE_FK + " INTEGER, "
            + " FOREIGN KEY ("+KEY_ID_LEK_FK+") REFERENCES "+TBL_LEK+"("+KEY_ID_LEK_PK+"), "
            + " FOREIGN KEY ("+KEY_ID_BILDE_FK+") REFERENCES "+TBL_BILDE+"("+KEY_ID_BILDE_PK+"));";

    public dbHandler (Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        //oppretter databasen
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL (LEK_TABLE_CREATE);
        db.execSQL (BILDE_TABLE_CREATE);
        db.execSQL (UTFALL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TBL_LEK);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_BILDE);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_UTFALL);
    }

}
