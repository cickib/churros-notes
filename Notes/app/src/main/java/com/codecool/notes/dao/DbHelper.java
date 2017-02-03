package com.codecool.notes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codecool.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

//TODO upgrade to prepared statements
public class DbHelper extends SQLiteOpenHelper {
    private static final String NOTES_TABLE_NAME = "notes";
    private static final String NOTES_COLUMN_ID = "id";
    private static final String NOTES_COLUMN_TEXT = "text";
    private static final String NOTES_COLUMN_DATE = "date";
    private static final String DATABASE_NAME = "notesApp.db";
    private final String TAG = this.getClass().getSimpleName();
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

    public boolean insertNote(String text, String date) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_TEXT, text);
        contentValues.put(NOTES_COLUMN_DATE, date);
        db.insert(NOTES_TABLE_NAME, null, contentValues);
        Log.d(TAG, "New record inserted to db (String args).");
        return true;
    }

    public boolean insertNote(Note note) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_TEXT, note.getText());
        contentValues.put(NOTES_COLUMN_DATE, note.getDate().toString());
        db.insert(NOTES_TABLE_NAME, null, contentValues);
        Log.d(TAG, "New record inserted to db (Note arg).");
        return true;
    }

    public Note getNoteById(int id) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTES_TABLE_NAME + " WHERE " + NOTES_COLUMN_ID + "=" + id + ";", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.d(TAG, "Note retrieved.");
            return new Note(cursor.getString(cursor.getColumnIndex(NOTES_COLUMN_TEXT)), cursor.getString(cursor.getColumnIndex(NOTES_COLUMN_DATE)));
        }
        Log.d(TAG, "No note found with id " + id + ".");
        return null;
    }

    public int numberOfRecords() {
        db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, NOTES_TABLE_NAME);
        return numRows;
    }

    public boolean updateNote(Integer id, String text) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_TEXT, text);
        db.update(NOTES_TABLE_NAME, contentValues, NOTES_COLUMN_ID + " = ? ;", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteNote(Integer id) {
        db = this.getWritableDatabase();
        return db.delete(NOTES_TABLE_NAME, NOTES_COLUMN_ID + "= ? ;", new String[]{Integer.toString(id)});
    }

    public List<Note> getAll() {
        List<Note> notes = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + NOTES_TABLE_NAME + ";", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            notes.add(new Note(res.getString(res.getColumnIndex(NOTES_COLUMN_TEXT)), res.getString(res.getColumnIndex(NOTES_COLUMN_DATE))));
            res.moveToNext();
        }
        Log.d(TAG, notes.size() + " notes retrieved from db.");
        return notes;
    }

    public List<String> stringifyNotes(List<Note> noteList) {
        List<String> notes = new ArrayList<>();
        if (noteList.size() > 0) {
            for (Note note : noteList) {
                notes.add(note.getText());
            }
        }
        Log.d(TAG, notes.size() + " notes stringified.");
        return notes;
    }

    public void dropDB(Context context) {
        context.deleteDatabase(DATABASE_NAME);
        Log.d(TAG, "Db '" + DATABASE_NAME + "' dropped.");
    }
}