package com.example.do_an.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.do_an.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDataSource {
    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    private String[] allColumns = {MyDatabaseHelper.COLUMN_USER_ID,
            MyDatabaseHelper.COLUMN_PHONE, MyDatabaseHelper.COLUMN_FULLNAME, MyDatabaseHelper.COLUMN_BIRTHDAY, MyDatabaseHelper.COLUMN_SEX, MyDatabaseHelper.COLUMN_ADDRESS};

    public UserInfoDataSource(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public UserInfo createUserInfo(String phone, String name, String birthday, String sex, String address) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_PHONE, phone);
        values.put(MyDatabaseHelper.COLUMN_FULLNAME, name);
        values.put(MyDatabaseHelper.COLUMN_BIRTHDAY, birthday);
        values.put(MyDatabaseHelper.COLUMN_SEX, sex);
        values.put(MyDatabaseHelper.COLUMN_ADDRESS, address);
        long insertId = database.insert(MyDatabaseHelper.TABLE_USER_INFO, null,
                values);
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_USER_INFO,
                allColumns, MyDatabaseHelper.COLUMN_USER_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        UserInfo newUserInfo = cursorToUserInfo(cursor);
        cursor.close();
        return newUserInfo;
    }

    public void updateUserInfo(UserInfo userInfo, String name, String birthday, String sex, String address) {
        ContentValues values = new ContentValues();
        if (name.equals(""))
            values.put(MyDatabaseHelper.COLUMN_FULLNAME, "Chưa thiết lập");
        else
            values.put(MyDatabaseHelper.COLUMN_FULLNAME, name);
        if (birthday.equals(""))
            values.put(MyDatabaseHelper.COLUMN_BIRTHDAY, "Chưa thiết lập");
        else
            values.put(MyDatabaseHelper.COLUMN_BIRTHDAY, birthday);
        if (sex.equals(""))
            values.put(MyDatabaseHelper.COLUMN_SEX, "Chưa thiết lập");
        else
            values.put(MyDatabaseHelper.COLUMN_SEX, sex);
        if (address.equals(""))
            values.put(MyDatabaseHelper.COLUMN_ADDRESS, "Chưa thiết lập");
        else
            values.put(MyDatabaseHelper.COLUMN_ADDRESS, address);
        String whereClause = MyDatabaseHelper.COLUMN_PHONE + " = ?";
        String[] whereArgs = {String.valueOf(userInfo.getPhone())};
        database.update(MyDatabaseHelper.TABLE_USER_INFO, values, whereClause, whereArgs);
    }

    public List<UserInfo> getAllUserInfos() {
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_USER_INFO,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserInfo userInfo = cursorToUserInfo(cursor);
            userInfos.add(userInfo);
            cursor.moveToNext();
        }
        cursor.close();
        return userInfos;

    }

    public void deleteUserInfo(String phone) {
        String whereClause = MyDatabaseHelper.COLUMN_PHONE + " = ?";
        String[] whereArgs = {phone};
        database.delete(MyDatabaseHelper.TABLE_USER_INFO, whereClause, whereArgs);
        Log.d("deluserInfos", "Ok");
    }

    private UserInfo cursorToUserInfo(Cursor cursor) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(cursor.getString(1));
        userInfo.setName(cursor.getString(2));
        userInfo.setBirthday(cursor.getString(3));
        userInfo.setSex(cursor.getString(4));
        userInfo.setAddress(cursor.getString(5));
        return userInfo;
    }
}
