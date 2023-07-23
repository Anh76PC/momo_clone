package com.example.do_an.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.do_an.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {
    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    private String[] allColumns = {MyDatabaseHelper.COLUMN_ID,
            MyDatabaseHelper.COLUMN_USERNAME, MyDatabaseHelper.COLUMN_PASSWORD};

    public UserDataSource(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public User createUser(String username, String password) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_USERNAME, username);
        values.put(MyDatabaseHelper.COLUMN_PASSWORD, password);
        long insertId = database.insert(MyDatabaseHelper.TABLE_USERS, null,
                values);
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_USERS,
                allColumns, MyDatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    public void deleteUser(User user) {
        String username = user.getUsername();
        String whereClause = MyDatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] whereArgs = { username };
        database.delete(MyDatabaseHelper.TABLE_USERS, whereClause, whereArgs);
        Log.d("delUser", "Ok");
    }
    public void updatePassword(User user, String newPassword) {
        String username = user.getUsername();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_PASSWORD, newPassword);
        String whereClause = MyDatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] whereArgs = { username };
        database.update(MyDatabaseHelper.TABLE_USERS, values, whereClause, whereArgs);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        Cursor cursor = database.query(MyDatabaseHelper.TABLE_USERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        return user;
    }
}
