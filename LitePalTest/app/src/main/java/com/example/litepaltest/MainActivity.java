package com.example.litepaltest;

import java.util.List;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;
import org.litepal.tablemanager.Connector;

import androidx.appcompat.app.AppCompatActivity;
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
    Button createButton = (Button) findViewById(R.id.create_database);
    createButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Connector.getDatabase();
      }
    });
    Button addData = (Button) findViewById(R.id.add_data);
    addData.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Book book = new Book();
        book.setName("The First Line Code");
        book.setAuthor("Guolin");
        book.setId(13445);
        book.setPage(475);
        book.setPrice(69.5);
        book.save();
      }
    });

    Button updateButton = (Button) findViewById(R.id.updateData);
    updateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Book book = new Book();
        book.setPrice(14.56);
        book.updateAll("page = ?", "475");
      }
    });

    Button deleteButton = (Button) findViewById(R.id.delete_data);
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LitePal.deleteAll("Book","page>200");
      }
    });

    Button queryButton = (Button)findViewById(R.id.query_data);
    queryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        List<Book>books = LitePal.findAll(Book.class);
        for(Book book:books){
          Log.d(TAG, "name "+book.getName());
          Log.d(TAG, "author "+book.getAuthor());
          Log.d(TAG, "page "+book.getPage());
          Log.d(TAG, "price "+book.getPrice());
          Log.d(TAG, "id "+book.getId());
        }
      }
    });

  }
}