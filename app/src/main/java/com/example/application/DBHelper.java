package com.example.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String ID_COL = "id";
    private static final String TABLE_NAME = "users";
    private static final String NAME_COL = "name";
    private static final String SURNAME_COL ="surname";
    private static final String PASSWORD_COL = "password";
    private static final String USERNAME_COL = "username";
    private static final String EMAIL_COL = "email";
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + SURNAME_COL + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + USERNAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL,user.getName());
        values.put(SURNAME_COL,user.getSurname());
        values.put(USERNAME_COL,user.getPhone());
        values.put(PASSWORD_COL,user.getPassword());
        values.put(EMAIL_COL,user.getEmail());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public User getUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectingArgs={username};

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME_COL + "= ?",selectingArgs);
        if (cursor.moveToFirst()) {
            User user = new User(cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(5),cursor.getString(3));
            db.close();
            return user;
        }
        db.close();
        return null;
    }

    public boolean checkUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs ={username};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME_COL + "= ?",selectionArgs);
        if (cursor.getCount()>0) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }
}
