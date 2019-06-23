package com.example.sergio.enquete_rer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SNCF.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseManager( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "create table Candidat ("
                + "idCandidat integer not null primary key AUTOINCREMENT,"
                + "nom varchar(255) not null,"
                + "prenom varchar(255) not null,"
                + "frequence varchar(255) not null,"
                + "profession varchar(255) not null,"
                + "tranche_age varchar(255) not null,"
                + "moyenne float not null,"
                + "smiley varchar(255) not null,"
                + "rer varchar(255) not null"
                + ")";
        db.execSQL( strSql );
        Log.i( "DATABASE", "onCreate invoked" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //String strSql = "alter table T_Scores add column ...";
        String strSql = "drop table Candidat";
        db.execSQL( strSql );
        this.onCreate( db );
        Log.i( "DATABASE", "onUpgrade invoked" );
    }

    public void deleteCandidat(String nom) {
        nom = nom.replace("'", "''");

        String strSql = "Delete from Candidat where (nom='" + nom + "')";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", "database invoked");
    }

    public void insertCandidat( String nom, String prenom, String frequence, String profession,
                                String tranche_age, float moyenne, String smiley, String rer) {
        nom = nom.replace( "'", "''" );
        prenom = prenom.replace("'", "''");
        frequence = frequence.replace("'", "''");
        profession = profession.replace("'", "''");
        tranche_age = tranche_age.replace("'", "''");
        smiley = smiley.replace("'", "''");
        rer = rer.replace("'", "''");

        String strSql = "insert into Candidat values ("+(this.getNbCandidat()+1)+",'"
                + nom + "', '" + prenom +"', '" + frequence +"', '" + profession +"'," +
                " '" + tranche_age + "', " + moyenne +", '" + smiley +"', '" + rer +"')";
        this.getWritableDatabase().execSQL( strSql );
        Log.i( "DATABASE", "database invoked" );
    }

    public void selectCandidat(String nom) {

        String strSql = "select nom from Candidat where'" + nom +"';";
        this.getWritableDatabase().execSQL( strSql );
        Log.i( "DATABASE", "database delete" );
    }

    public int getNbCandidat() {

        List<Candidat> moyenne = new ArrayList<>();

        Cursor cursor = this.getReadableDatabase().query( "Candidat",
                new String[] { "idCandidat", "nom", "prenom", "frequence", "profession",
                        "tranche_age", "moyenne", "smiley", "rer" },
                null, null, null, null, "moyenne desc", "10" );
        cursor.moveToFirst();
        while( ! cursor.isAfterLast() ) {
            cursor.moveToNext();
        }
        int nb = cursor.getPosition();
        cursor.close();
        return nb;
    }

    public List<Candidat> readTop10() {
        List<Candidat> moyenne = new ArrayList<>();

        Cursor cursor = this.getReadableDatabase().query( "Candidat",
                new String[] { "idCandidat", "nom", "prenom", "frequence", "profession",
                               "tranche_age", "moyenne", "smiley", "rer" },
                null, null, null, null, "moyenne desc", "10" );
        cursor.moveToFirst();
        while( ! cursor.isAfterLast() ) {
            Candidat candidat = new Candidat( cursor.getString( 1 ),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getFloat(6),
                    cursor.getString(7),
                    cursor.getString(8) );
            moyenne.add(candidat);
            cursor.moveToNext();
        }
        cursor.close();

        return moyenne;
    }

}
