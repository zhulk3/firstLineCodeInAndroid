package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
    Button button = (Button) findViewById(R.id.create_database);
    Button addDataButton = (Button) findViewById(R.id.add_data);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        myDatabaseHelper.getWritableDatabase();
      }
    });

    addDataButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        contentValues.put("name", "the Da Vinci Code");
        contentValues.put("author", "Dan Brown");
        contentValues.put("page", 454);
        contentValues.put("price", 16.96);
        sqLiteDatabase.insert("B00k", null, contentValues);
        contentValues.clear();
        contentValues.put("name", "Monna lisa");
        contentValues.put("author", "kaienci");
        contentValues.put("price", 56);
        contentValues.put("page", 471);
        sqLiteDatabase.insert("Book", null, contentValues);

      }
    });

    Button updateData = findViewById(R.id.updateData);
    updateData.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("price", 10);
        db.update("Book", contentValues, "name = ?", new String[]{"The Dan Vinci Code"});
      }
    });

    Button deleteData = (Button) findViewById(R.id.delete_data);
    deleteData.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        db.delete("Book", "price>?", new String[]{"50"});
      }
    });

    Button queryData = (Button) findViewById(R.id.query_data);
    queryData.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        Cursor cursor =
            db.query("Book", null,null,null, null, null,
                null);
        if(cursor.moveToFirst()){
          do {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String author =cursor.getString(cursor.getColumnIndex("author"));
            int page = cursor.getInt(cursor.getColumnIndex("page"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            Log.d(TAG, "name "+name);
            Log.d(TAG, "author "+author);
            Log.d(TAG, "page "+page);
            Log.d(TAG, "price "+price );
          }while (cursor.moveToNext());
        }
        cursor.close();
      }
    });

  }

}