package com.codecool.notes;

import android.util.Log;

import com.codecool.notes.dao.DbHelper;
import com.codecool.notes.model.Note;

import java.util.Collections;
import java.util.List;

public class NoteSorter {
    private final String TAG = this.getClass().getSimpleName();
    private DbHelper dbHelper;
    private String orderString;

    public NoteSorter(DbHelper dbHelper, String order) {
        this.dbHelper = dbHelper;
        this.orderString = order;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }

    private SortOption convertSort() {
        String mode = orderString.toString().split("\\s+")[0];
        String order = orderString.toString().split("\\s+")[1];
        if (order.equals("▲") || order.equals("&#9650;")) {
            order = "ASC";
        } else {
            order = "DESC";
        }
        return new SortOption(mode.toUpperCase(), order.toUpperCase());
    }

    public List<String> sort() {
        Log.d(TAG, "Sorted " + convertSort().getMode() + convertSort().getOrder() + ".");
        List<Note> sorted = dbHelper.getAll();
        switch (convertSort().getMode()) {
            case "ABC":
                Collections.sort(sorted, (note1, note2) -> note1.getText().compareTo(note2.getText()));
                break;
            case "DATE":
                Collections.sort(sorted, (note1, note2) -> note1.getDate().compareTo(note2.getDate()));
                break;
        }
        if (convertSort().getOrder().equals("DESC")) {
            Collections.reverse(sorted);
        }
        return dbHelper.stringifyNotes(sorted);
    }
}