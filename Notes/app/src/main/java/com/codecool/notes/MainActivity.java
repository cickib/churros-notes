package com.codecool.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "App launched.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddNew(View view) {
        Log.d(TAG, "Add new clicked.");
        Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show();
    }

    public void onClickSort(View view) {
        String order = view.getTag().toString();
        Log.d(TAG, "Sort button clicked. Requested order: " + order);
        Toast.makeText(this, order, Toast.LENGTH_SHORT).show();
    }
}
