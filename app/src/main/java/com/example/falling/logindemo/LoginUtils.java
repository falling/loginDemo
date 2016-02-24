package com.example.falling.logindemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by falling on 2016/2/23.
 */
public class LoginUtils {
    private LoginSQLiteOpenHelper mLoginSQLiteOpenHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private Context mContext;

    public LoginUtils(Context context) {
        mContext = context;
        mLoginSQLiteOpenHelper = new LoginSQLiteOpenHelper(mContext);
        mSqLiteDatabase = mLoginSQLiteOpenHelper.getWritableDatabase();
    }

    /**
     * 登陆时插入到数据库
     *
     * @param mEmail 用户名
     */
    public void insert(String mEmail ,String mPassword) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoginSQLiteOpenHelper.USERNAME, mEmail);
        contentValues.put(LoginSQLiteOpenHelper.PASSWORD, mPassword);
        mSqLiteDatabase.insert(LoginSQLiteOpenHelper.USER_TABLE_NAME, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(LoginSQLiteOpenHelper.USERNAME, mEmail);
        contentValues.put(LoginSQLiteOpenHelper.ID, 1);
        contentValues.put(LoginSQLiteOpenHelper.LOGIN_STATE, true);
        mSqLiteDatabase.insert(LoginSQLiteOpenHelper.LAST_LOGIN_TABLE_NAME, null, contentValues);
    }

    /**
     * 获取登陆用户名历史记录
     *
     * @return 用户名的 ArrayList
     */
    public ArrayList<String> getHistoryAll() {
        ArrayList<String> result = new ArrayList<>();
        Cursor cursor = mSqLiteDatabase.query(
                LoginSQLiteOpenHelper.USER_TABLE_NAME,
                new String[]{LoginSQLiteOpenHelper.USERNAME},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        return result;
    }

    /**
     * 获取最后登陆的用户名
     *
     * @return 最后登陆的用户名
     */
    public String getLastLoginUserName() {
        String result = "";
        Cursor cursor = mSqLiteDatabase.query(LoginSQLiteOpenHelper.LAST_LOGIN_TABLE_NAME,
                new String[]{LoginSQLiteOpenHelper.USERNAME},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }
        return result;
    }

    /**
     * 获取登陆状态的用户名
     *
     * @return 登陆状态的用户名
     */
    public String getLoginedUserName() {
        String result = "";
        Cursor cursor = mSqLiteDatabase.query(LoginSQLiteOpenHelper.LAST_LOGIN_TABLE_NAME,
                new String[]{LoginSQLiteOpenHelper.USERNAME},
                LoginSQLiteOpenHelper.LOGIN_STATE + " = 1", null, null, null, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }
        cursor.close();
        return result;
    }

    public void sighOut(String userName) {
        mSqLiteDatabase.execSQL("update " + LoginSQLiteOpenHelper.LAST_LOGIN_TABLE_NAME + " set " + LoginSQLiteOpenHelper.LOGIN_STATE+" =0 where " + LoginSQLiteOpenHelper.USERNAME + " = '" + userName+"'");
    }
}
