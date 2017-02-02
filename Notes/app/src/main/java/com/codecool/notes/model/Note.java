package com.codecool.notes.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private final String TAG = this.getClass().getSimpleName();
    private String text;
    private Date date;
    private SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

    public Note(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Note(String text, String date) {
        this.text = text;
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
