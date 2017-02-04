package com.codecool.notes;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codecool.notes.dao.DbHelper;
import com.codecool.notes.model.Note;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private String order = "Date ▲";
    private NoteAdapter noteAdapter;
    private DbHelper dbHelper;
    private ListView listViewNotes;
    private Spinner spinner;
    private NoteSorter noteSorter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "App launched.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this);
        noteSorter = new NoteSorter(dbHelper, order);
        listViewNotes = (ListView) findViewById(R.id.listview_notes);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.sort_spinner);
        spinner.setAdapter(sortAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    noteSorter.setOrderString(item.toString());
                    updateUi(noteSorter.sort());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        updateUi(noteSorter.sort());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "Options menu created.");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "'" + item + "' selected from options menu.");
        switch (item.getItemId()) {
            case R.id.menu_add:
                final EditText noteEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.add_dialog_title)
                        .setView(noteEditText)
                        .setPositiveButton(R.string.save,
                                (dialog1, which) -> {
                                    String note = String.valueOf(noteEditText.getText());
                                    Log.d(TAG, "Note to save: " + note);
                                    dbHelper.insertNote(new Note(note, new Date()));
                                    updateUi(noteSorter.sort());
                                })
                        .setNegativeButton(R.string.cancel, null).create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUi(List<Note> notes) {
        Log.d(TAG, "Ui updated.");
        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(this, R.layout.note_item, notes);
            listViewNotes.setAdapter(noteAdapter);
        } else {
            noteAdapter.clear();
            noteAdapter.addAll(notes);
            noteAdapter.notifyDataSetChanged();
        }
    }

//    TODO implement deletion
}
