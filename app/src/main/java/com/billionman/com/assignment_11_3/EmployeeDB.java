package com.billionman.com.assignment_11_3;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CTM on 20-06-2017.
 */

public class EmployeeDB extends SQLiteOpenHelper {
    public EmployeeDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public EmployeeDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public EmployeeDB(Context context) {
        super(context, "EmployeeDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE EMP_TABLE (EMP_NAME TEXT PRIMARY KEY, EMP_AGE INTEGER NOT NULL, EMP_PHOTO BLOB)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
