package com.codecool.notes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codecool.notes.dao.DbHelper;
import com.codecool.notes.model.Note;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayAdapter<String> adapter;
    private DbHelper dbHelper;
    private ListView listViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "App launched.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this);
        listViewNotes = (ListView) findViewById(R.id.listview_notes);
        updateUi(dbHelper.stringifyNotes());
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
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String note = String.valueOf(noteEditText.getText());
                                        Log.d(TAG, "Note to save: " + note);
                                        dbHelper.insertNote(new Note(note, new Date()));
                                        updateUi(dbHelper.stringifyNotes());
                                    }
                                })
                        .setNegativeButton(R.string.cancel, null).create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickSort(View view) {
        String order = view.getTag().toString();
        Log.d(TAG, "Sort button clicked. Requested order: " + order);
        Toast.makeText(this, order, Toast.LENGTH_SHORT).show();
    }

    private void updateUi(List<String> notes) {
        Log.d(TAG, "Ui updated.");
        if (adapter == null) {
            adapter = new ArrayAdapter<String>(this, R.layout.note_item, R.id.note_text, notes);
            listViewNotes.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(notes);
            adapter.notifyDataSetChanged();
        }
    }
}
