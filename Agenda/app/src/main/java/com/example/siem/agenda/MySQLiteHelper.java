package com.example.siem.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by siem on 17/02/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "homeworkdatabase.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_ASSIGNMENTS = "assignments";
    public static final String COLUMN_ASSIGNMENT_ID = "assignment_id";
    public static final String COLUMN_ASSIGNMENT = "assignment";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TITLE = "title";

    //create the database table
    private static final String DATABASE_CREATE_ASSIGNMENTS =
            "CREATE TABLE " + TABLE_ASSIGNMENTS +
                    "(" +
                        COLUMN_ASSIGNMENT_ID + " integer primary key autoincrement, " +
                        COLUMN_ASSIGNMENT + " text not null, " +
                        COLUMN_DATE + " text not null, " +
                        COLUMN_TITLE + " text not null" +
                    ");";

    public MySQLiteHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {

        //execute sql to create tables
        database.execSQL(DATABASE_CREATE_ASSIGNMENTS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);
        onCreate(db);
    }
}
