package com.codecool.notes.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.codecool.notes.R;
import com.codecool.notes.model.Note;

import java.util.List;

/**
 * Custom Adapter for displaying the Note instances on the screen.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    private final Context context;
    private final int resource;
    private final List<Note> objects;

    public NoteAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.textViewId = (TextView) row.findViewById(R.id.note_id);
            holder.textViewText = (TextView) row.findViewById(R.id.note_text);
            holder.textViewDate = (TextView) row.findViewById(R.id.note_date);
            holder.deleteButton = (Button) row.findViewById(R.id.delete_note);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Note note = objects.get(position);
        holder.textViewId.setText(String.valueOf(note.getId()));
        holder.textViewText.setText(note.getText());
        holder.textViewDate.setText(String.valueOf(note.getDate()));
        holder.deleteButton.setText(R.string.delete_note);
        return row;
    }

    private static class ViewHolder {
        private TextView textViewId;
        private TextView textViewText;
        private TextView textViewDate;
        private Button deleteButton;
    }
}
