package com.example.administrador.myapplication.model.persistence;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrador.myapplication.model.persistence.client.ClientContract;
import com.example.administrador.myapplication.model.persistence.user.UserContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "MY_DATABASE";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.BANCO_DADOS, null, DatabaseHelper.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClientContract.getSqlCreateTable());
        db.execSQL(UserContract.getSqlCreateTable());
        db.execSQL(UserContract.insertIntoUserTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
