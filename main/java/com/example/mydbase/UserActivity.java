package com.example.mydbase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    EditText name,year;
    Button save,delete;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        name = findViewById(R.id.name);
        year = findViewById(R.id.year);
        save = findViewById(R.id.btnSave);
        delete = findViewById(R.id.btnDelete);
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        if (userId > 0) {
            userCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE + " WHERE "
                    + DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)} );
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
