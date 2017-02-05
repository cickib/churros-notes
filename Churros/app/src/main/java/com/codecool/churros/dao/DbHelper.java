package com.codecool.churros.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codecool.churros.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements all necessary methods to interact with the database of the application.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "churrosApp.db";
    private final String TAG = this.getClass().getSimpleName();
    private final String NOTES_TABLE_NAME = "notes";
    private final String NOTES_COLUMN_ID = "id";
    private final String NOTES_COLUMN_TEXT = "text";
    private final String NOTES_COLUMN_DATE = "date";
    private SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d(TAG, "New db '" + DATABASE_NAME + "' created.");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + NOTES_TABLE_NAME +
                " (" + NOTES_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                NOTES_COLUMN_TEXT + " text, " +
                NOTES_COLUMN_DATE + " text);");
        Log.d(TAG, "Table '" + NOTES_TABLE_NAME + "' created in db '" + DATABASE_NAME + "'.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    public void insertNote(Note note) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_TEXT, note.getText());
        contentValues.put(NOTES_COLUMN_DATE, note.getDate().toString());
        try {
            db.insert(NOTES_TABLE_NAME, null, contentValues);
            Log.d(TAG, "New record inserted to db (Note arg).");
        } catch (SQLiteException e) {
            Log.e(TAG, "Failed to insert to " + NOTES_TABLE_NAME + ". " + e.getMessage());
        }
    }

    public Integer deleteNote(int id) {
        db = this.getWritableDatabase();
        return db.delete(NOTES_TABLE_NAME, NOTES_COLUMN_ID + "= ? ;", new String[]{Integer.toString(id)});
    }

    public List<Note> getAll() {
        List<Note> notes = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + NOTES_TABLE_NAME + ";", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            notes.add(new Note(res.getInt(res.getColumnIndex(NOTES_COLUMN_ID)), res.getString(res.getColumnIndex(NOTES_COLUMN_TEXT)), res.getString(res.getColumnIndex(NOTES_COLUMN_DATE))));
            res.moveToNext();
        }
        Log.d(TAG, notes.size() + " notes retrieved from db.");
        return notes;
    }

    public void clearTable() {
        db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE_NAME + ";");
        Log.d(TAG, "Table '" + NOTES_TABLE_NAME + "' dropped.");
        onCreate(db);
    }
}