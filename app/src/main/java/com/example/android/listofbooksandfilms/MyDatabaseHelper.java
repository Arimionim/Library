package com.example.android.listofbooksandfilms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    MyDatabaseHelper(Context context, final String DATABASE_NAME, final int DATABASE_VERSION)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating database with fields:
        db.execSQL("create table Books("
                + "id integer primary key autoincrement,"
                + "main text,"
                + "additional text,"
                + "description text,"
                + "rate num,"
                + "good bit" + ");")
        ;
        db.execSQL("create table Films ("
                + "id integer primary key autoincrement,"
                + "main text,"
                + "additional text,"
                + "description text,"
                + "rate num,"
                + "good bit" + ");")
        ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
