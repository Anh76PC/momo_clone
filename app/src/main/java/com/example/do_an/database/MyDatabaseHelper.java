package com.example.do_an.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 22; // Tăng phiên bản cơ sở dữ liệu lên 2

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";


    // Thêm hằng số cho bảng user_info và các cột tương ứng
    public static final String TABLE_USER_INFO = "user_info";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_FULLNAME = "name";
    public static final String COLUMN_BIRTHDAY = "birthday";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_ADDRESS = "address";


    // Thêm hằng số cho bảng car_info và các cột tương ứng
    public static final String TABLE_CAR_INFO = "car_info";

    public static final String COLUMN_CAR_ID = "car_id";
    public static final String COLUMN_CAR_NAME = "nameCar";
    public static final String COLUMN_CAR_BIENSO = "bienso";
    public static final String COLUMN_CAR_DTXL = "dtxl";
    public static final String COLUMN_CAR_USER = "carUser";

    // Thêm hằng số cho bảng car_his và các cột tương ứng
    public static final String TABLE_CAR_HIS = "car_his";

    public static final String COLUMN_CAR_HIS_ID = "his_id";
    public static final String COLUMN_CAR_HIS_NAMELOCAL = "namlocal";
    public static final String COLUMN_CAR_HIS_DATEFIX = "datafix";
    public static final String COLUMN_CAR_HIS_MOTA = "mota";
    public static final String COLUMN_CAR_HIS_STATUS = "status";
    public static final String COLUMN_CAR_HIS_HISUSER = "hisuser";

    // Câu lệnh tạo bảng car_his
    private static final String CREATE_CAR_HIS_TABLE =
            "CREATE TABLE " + TABLE_CAR_HIS + "(" +
                    COLUMN_CAR_HIS_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_CAR_HIS_NAMELOCAL + " TEXT, " +
                    COLUMN_CAR_HIS_DATEFIX + " TEXT," +
                    COLUMN_CAR_HIS_MOTA + " TEXT," +
                    COLUMN_CAR_HIS_STATUS + " TEXT, " +
                    COLUMN_CAR_HIS_HISUSER + " TEXT)";

    // Câu lệnh tạo bảng user_info
    private static final String CREATE_USER_INFO_TABLE =
            "CREATE TABLE " + TABLE_USER_INFO + "(" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_PHONE + " TEXT NOT NULL, " +
                    COLUMN_FULLNAME + " TEXT," +
                    COLUMN_BIRTHDAY + " TEXT," +
                    COLUMN_SEX + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT)";

    // Câu lệnh tạo bảng car_info
    private static final String CREATE_CAR_INFO_TABLE =
            "CREATE TABLE " + TABLE_CAR_INFO + "(" +
                    COLUMN_CAR_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_CAR_NAME + " TEXT, " +
                    COLUMN_CAR_BIENSO + " TEXT," +
                    COLUMN_CAR_DTXL + " TEXT," +
                    COLUMN_CAR_USER + " TEXT)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng users
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL)");

        // Tạo bảng user_info
        db.execSQL(CREATE_USER_INFO_TABLE);
        // Tạo bảng car_info
        db.execSQL(CREATE_CAR_INFO_TABLE);
        // Tạo bảng car_his
        db.execSQL(CREATE_CAR_HIS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR_HIS);
        onCreate(db);
    }
}


