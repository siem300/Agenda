package com.example.siem.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by siem on 17/02/2016.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] assignmentAllColumns = {
            MySQLiteHelper.COLUMN_ASSIGNMENT_ID, MySQLiteHelper.COLUMN_ASSIGNMENT
    };

    public DataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public long createAssignment(String assignment){
        if(!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ASSIGNMENT, assignment);
        long insertId = database.insert(MySQLiteHelper.TABLE_ASSIGNMENTS, null, values);

        if(database.isOpen())
            close();

        return insertId;
    }

    public void deleteAssignment(Assignment assignment){
        if(!database.isOpen())
            open();

        database.delete(MySQLiteHelper.TABLE_ASSIGNMENTS, MySQLiteHelper.COLUMN_ASSIGNMENT_ID + " =?",
                new String[]{
                        Long.toString(assignment.getId())});

        if(database.isOpen())
            close();

    }

    public void updateAssignment(Assignment assignment){
        if(!database.isOpen())
            open();

        ContentValues args = new ContentValues();
        args.put(MySQLiteHelper.COLUMN_ASSIGNMENT, assignment.getAssignment());
        database.update(MySQLiteHelper.TABLE_ASSIGNMENTS, args, MySQLiteHelper.COLUMN_ASSIGNMENT_ID + " =?",
                new String[]{Long.toString(assignment.getId())});
        if(database.isOpen())
            close();
    }

    public List<Assignment> getAllAssignments(){
        if(!database.isOpen())
            open();

        List<Assignment> assignments = new ArrayList<Assignment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSIGNMENTS,
                assignmentAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Assignment assignment = cursorToAssignment(cursor);
            assignments.add(assignment);
            cursor.moveToNext();
        }

        cursor.close();

        if(database.isOpen())
            close();

        return assignments;

    }

    private Assignment cursorToAssignment(Cursor cursor){
        try{
            Assignment assignment = new Assignment();
            assignment.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ASSIGNMENT_ID)));
            assignment.setAssignment(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ASSIGNMENT)));
                return assignment;
        }catch(CursorIndexOutOfBoundsException exception){
            exception.printStackTrace();
            return null;
        }

    }

    public Assignment getAssignment(long columnId) {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSIGNMENTS, assignmentAllColumns, MySQLiteHelper.COLUMN_ASSIGNMENT_ID + "=?", new String[] { Long.toString(columnId)}, null, null, null);

        cursor.moveToFirst();
        Assignment assignment = cursorToAssignment(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return assignment;
    }
}
