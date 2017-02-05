package com.codecool.churros;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codecool.churros.dao.DbHelper;
import com.codecool.churros.model.Note;
import com.codecool.churros.util.NoteAdapter;
import com.codecool.churros.util.NoteSorter;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private String order = "▼ Date";
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
        spinner = createSortSpinner();
        updateUi(noteSorter.sort());
    }

    private void updateUi(List<Note> notes) {
        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(this, R.layout.note_item, notes);
            listViewNotes.setAdapter(noteAdapter);
        } else {
            noteAdapter.clear();
            noteAdapter.addAll(notes);
            noteAdapter.notifyDataSetChanged();
        }
        Log.d(TAG, "Ui updated.");
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
                return addMenu();
            case R.id.menu_clear_all:
                return clearMenu();
            case R.id.menu_info:
                return infoMenu();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean addMenu() {
        final EditText noteEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.add_dialog_title)
                .setView(noteEditText)
                .setPositiveButton(R.string.save,
                        (dialog1, which) -> {
                            String note = String.valueOf(noteEditText.getText());
                            if (note.length() > 0) {
                                Log.d(TAG, "Note to save: " + note);
                                dbHelper.insertNote(new Note(note, new Date()));
                                updateUi(noteSorter.sort());
                            }
                        })
                .setNegativeButton(R.string.cancel, null).create();
        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorDark));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccentDarker));
        });
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
        return true;
    }

    private boolean infoMenu() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.github, null);
        AlertDialog dialogGitHub = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.github)
                .setPositiveButton(R.string.yes,
                        (dialog1, which) -> {
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("https://github.com/CodecoolBP20161/advanced-teaser-cickib"));
                            startActivity(intent);
                        })
                .setNegativeButton(R.string.cancel, null).create();
        dialogGitHub.setOnShowListener(arg0 -> {
            dialogGitHub.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorDark));
            dialogGitHub.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccentDarker));
        });
        dialogGitHub.show();
        return true;
    }

    private boolean clearMenu() {
        AlertDialog dialogClear = new AlertDialog.Builder(this)
                .setTitle(R.string.clear_all)
                .setPositiveButton(R.string.yes,
                        (dialog1, which) -> {
                            dbHelper.clearTable();
                            updateUi(noteSorter.sort());
                            Toast.makeText(MainActivity.this, "All clear. Yay!", Toast.LENGTH_SHORT).show();
                        })
                .setNegativeButton(R.string.cancel, null).create();
        dialogClear.setOnShowListener(arg0 -> {
            dialogClear.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorDark));
            dialogClear.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccentDarker));
        });
        dialogClear.show();
        return true;
    }

    private Spinner createSortSpinner() {
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.sort_spinner);
        spinner.setAdapter(sortAdapter);
        spinner.setSelection(sortAdapter.getPosition(order));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    noteSorter.setOrderString(item.toString());
                    updateUi(noteSorter.sort());
                    order = item.toString();
                    Toast.makeText(MainActivity.this, "Notes sorted " + order, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return spinner;
    }

    public void deleteNote(View view) {
        View parent = (View) view.getParent();
        int noteId = Integer.parseInt(((TextView) parent.findViewById(R.id.note_id)).getText().toString());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_dialog)
                .setPositiveButton(R.string.yes,
                        (dialog1, which) -> {
                            dbHelper.deleteNote(noteId);
                            updateUi(noteSorter.sort());
                            Toast.makeText(MainActivity.this, "Note deleted. Yay!", Toast.LENGTH_SHORT).show();
                        })
                .setNegativeButton(R.string.cancel, null).create();
        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorDark));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccentDarker));
        });
        dialog.show();
    }
}
