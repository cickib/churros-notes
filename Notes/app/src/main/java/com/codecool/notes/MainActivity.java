package com.codecool.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codecool.notes.dao.DbHelper;
import com.codecool.notes.model.Note;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "App launched.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "Options menu created.");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickAddNew(View view) {
        Log.d(TAG, "Add new clicked.");
        String text = ((EditText) findViewById(R.id.editTextNote)).getText().toString();
        ((EditText) findViewById(R.id.editTextNote)).setText("");
        dbHelper.insertNote(new Note(text, new Date()));
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onClickSort(View view) {
        String order = view.getTag().toString();
        Log.d(TAG, "Sort button clicked. Requested order: " + order);
        Toast.makeText(this, order, Toast.LENGTH_SHORT).show();
    }
}
