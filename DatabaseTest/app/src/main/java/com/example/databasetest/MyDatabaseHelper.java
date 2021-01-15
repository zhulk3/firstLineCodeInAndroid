package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
  public static final String CREATE_BOOk = "create table Book(\n" +
      "      id integer primary key autoincrement,\n" +
      "      author text,\n" +
      "      price real,\n" +
      "      page integer,\n" +
      "      name text\n" +
      "  )";

  public static final String CREATE_CATEGORY = "create table Category(\n" +
      "      id integer primary key autoincrement,\n" +
      "      category_name text,\n" +
      "      category_code integer\n" +
      "  )";

  private Context mContext;

  public MyDatabaseHelper(@Nullable Context context,
      @Nullable String name,
      @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
    mContext=context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_BOOk);
    db.execSQL(CREATE_CATEGORY);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists Book");
    db.execSQL("drop table if exists Category");
    onCreate(db);
  }
}
