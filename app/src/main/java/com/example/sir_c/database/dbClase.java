package com.example.sir_c.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Raul on 29/05/2017.
 */

public class dbClase extends SQLiteOpenHelper{
    public dbClase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creamos la tabla de alumno
        /*tbAalumno     tabla
        -----------------------
        * PK|idCurp     |   texto
        *   |strNombre  |   Texto
        *   |strApat    |   Texto
        *   |strAmat    |   Texto
        * */
        db.execSQL("create table tbAalumno(" +
                "idCurp text primary key, " +
                "strNombre text, " +
                "strApat text, " +
                "strAmat text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //Si existe la tabla la desechamos
        db.execSQL("drop table " +
                "if exists tbAalumno");

        //Volvemos a crear la tabla
        db.execSQL("create table tbAalumno(" +
                "idCurp text primary key, " +
                "strNombre text, " +
                "strApat text, " +
                "strAmat text)");


    }
}
