package com.codecool.notes.model;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {
    private final String TAG = this.getClass().getSimpleName();
    private String text;
    private Date date;
    private DateFormat format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

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
