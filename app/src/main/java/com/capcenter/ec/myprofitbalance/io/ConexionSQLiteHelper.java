package com.capcenter.ec.myprofitbalance.io;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper( Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Utilidades.NOMBRE_BD, factory, Utilidades.VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_OPERACIONES);
        db.execSQL(Utilidades.CREAR_TABLA_CATEGORIAS);
        db.execSQL(Utilidades.CREAR_TABLA_CUENTAS);
        db.execSQL(Utilidades.INSERT_CUENTA);
        db.execSQL(Utilidades.INSERT_OPERACIONES0);
        db.execSQL(Utilidades.INSERT_OPERACIONES1);
        db.execSQL(Utilidades.INSERT_OPERACIONES2);
        db.execSQL(Utilidades.INSERT_OPERACIONES3);
        db.execSQL(Utilidades.INSERT_OPERACIONES4);
        db.execSQL(Utilidades.INSERT_OPERACIONES10);
        db.execSQL(Utilidades.INSERT_OPERACIONES11);
        db.execSQL(Utilidades.INSERT_OPERACIONES12);
        db.execSQL(Utilidades.INSERT_OPERACIONES13);
        db.execSQL(Utilidades.INSERT_OPERACIONES14);
        db.execSQL(Utilidades.INSERT_CAT0);
        db.execSQL(Utilidades.INSERT_CAT1);
        db.execSQL(Utilidades.INSERT_CAT2);
        db.execSQL(Utilidades.INSERT_CAT3);
        db.execSQL(Utilidades.INSERT_CAT4);
        db.execSQL(Utilidades.INSERT_CAT10);
        db.execSQL(Utilidades.INSERT_CAT11);
        db.execSQL(Utilidades.INSERT_CAT12);
        db.execSQL(Utilidades.INSERT_CAT13);
        db.execSQL(Utilidades.INSERT_CAT14);
        db.execSQL(Utilidades.INSERT_CAT15);
        db.execSQL(Utilidades.INSERT_CAT16);
        db.execSQL(Utilidades.INSERT_CAT20);
        db.execSQL(Utilidades.INSERT_CAT21);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_OPERACIONES);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CATEGORIAS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CUENTAS);
        onCreate(db);

    }
}
