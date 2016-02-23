package com.example.falling.logindemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by falling on 2016/2/22.
 */
public class LoginSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 2;
    public static final String LOGIN = "login.db";
    public static final String USER_TABLE_NAME = "table_users";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LAST_LOGIN_TABLE_NAME = "table_last_Login";
    public static final String ID = "_id";
    public static final String LOGIN_STATE = "state";

    public LoginSQLiteOpenHelper(Context context) {
        super(context, LOGIN, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE_NAME + "(" + USERNAME + " varchar(20) not null primary key ON CONFLICT REPLACE," + PASSWORD + ")");
        db.execSQL("create table " + LAST_LOGIN_TABLE_NAME + "(" + ID + " varchar(20) not null primary key ON CONFLICT REPLACE,"+ USERNAME+ "," + LOGIN_STATE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1){
            db.execSQL("create table " + LAST_LOGIN_TABLE_NAME + "(" + ID + " varchar(20) not null primary key ON CONFLICT REPLACE,"+ USERNAME+ "," + LOGIN_STATE + ")");
        }
    }
}
