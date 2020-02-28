package com.example.mydbase;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    SharedPreferences sharedPreferences;
    final String SAVED_TEXT = "saved_text";
    SQLiteDatabase db;
    Cursor query;
    ListView listView;
    DatabaseHelper databaseHelper;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        db = getBaseContext().openOrCreateDatabase("app.db",
                MODE_PRIVATE, null);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.list);

        /*db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT,age INTEGER)");
        db.execSQL("INSERT INTO users VALUES ('PAVEL',18);");
        db.execSQL("INSERT INTO users VALUES ('IVAN',35);");*/

        databaseHelper = new DatabaseHelper(getApplicationContext());

        loadText();
    }


    void loadText() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedText = sharedPreferences.getString(SAVED_TEXT, "");
        textView.setText(savedText);
        Toast.makeText(this, "Привет " + savedText, Toast.LENGTH_SHORT).show();

    }

    void saveText() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        int r = (int) (Math.random() * 1000000);
        databaseHelper.onUpgrade(db, 1,2);


        ed.putString(SAVED_TEXT, editText.getText().toString() + " " + r);
        ed.commit();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();
        query = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE, null);
        String[]  headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_NAME};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, query,
                headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        textView.setText("Найдено элементов " + query.getCount());
        listView.setAdapter(userAdapter);

    }

    public void Save(View view) {
        saveText();
        editText.setText("");
    }

    public void Load(View view) {
        loadText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
        query.close();
        db.close();
    }

    public void LoadDB(View view) {
        textView.setText("");
        query = db.rawQuery("SELECT * FROM users;", null);
        if (query.moveToFirst()) {
            do {
                textView.append("Name: " + query.getString(1) +
                        " Age: " + query.getInt(2) + "\n");

            } while (query.moveToNext());
        }


    }
}
