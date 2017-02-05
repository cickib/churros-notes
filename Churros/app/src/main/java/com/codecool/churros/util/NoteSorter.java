package com.codecool.churros.util;

import android.util.Log;

import com.codecool.churros.dao.DbHelper;
import com.codecool.churros.model.Note;

import java.util.Collections;
import java.util.List;

/**
 * Implements sorting for different options: ascending and descending order,
 * both alphabetically and based on the date of creation of a Note.
 */
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
        String mode = orderString.toString().split("\\s+")[1];
        String order = orderString.toString().split("\\s+")[0];
        if (order.equals("▲") || order.equals("&#9650;")) {
            order = "ASC";
        } else {
            order = "DESC";
        }
        return new SortOption(mode.toUpperCase(), order.toUpperCase());
    }

    public List<Note> sort() {
        List<Note> sorted = dbHelper.getAll();
        if (sorted.size() > 0) {
            switch (convertSort().mode) {
                case "ABC":
                    Collections.sort(sorted, (note1, note2) -> note1.getText().compareTo(note2.getText()));
                    break;
                case "DATE":
                    Collections.sort(sorted, (note1, note2) -> note1.getDate().compareTo(note2.getDate()));
                    break;
            }
            if (convertSort().order.equals("DESC")) {
                Collections.reverse(sorted);
            }
            Log.d(TAG, "Sorted " + convertSort().mode + " " + convertSort().order + ".");
        }
        return sorted;
    }

    /**
     * Helper class to differentiate between sorting options easily.
     */
    private class SortOption {
        private String mode;
        private String order;

        private SortOption(String mode, String order) {
            this.mode = mode;
            this.order = order;
        }
    }
}