package com.example.contactstest;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  private ArrayAdapter<String> mAdapter;
  private List<String> contactList = new ArrayList<>();
  private Button mAddContact;
  private Button mDeleteContact;
  private Button mUpdateContact;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mAddContact = (Button) findViewById(R.id.add_contact);
    mDeleteContact = (Button) findViewById(R.id.delete_contact);
    mUpdateContact = (Button) findViewById(R.id.update_contact);

    ListView contactsView = (ListView) findViewById(R.id.contacts_view);
    mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
    contactsView.setAdapter(mAdapter);
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
        PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
    } else {
      readContacts();
    }
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) !=
        PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 2);
    }else {
      writeContact();
    }

  }

  private void readContacts() {
    Cursor cursor = null;
    try {
      cursor = getContentResolver()
          .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
      if (cursor != null) {
        while (cursor.moveToNext()) {
          String name = cursor.getString(
              cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
          String number = cursor
              .getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
          contactList.add(name + '\n' + number);
        }
        mAdapter.notifyDataSetChanged();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  private void writeContact(){
    mAddContact.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, "goudan");
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, "18011849685");
        getContentResolver().insert(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, values);
        readContacts();
      }
    });

    mDeleteContact.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getContentResolver().delete(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "= ?", new String[]{"goudan"});
      }
    });

    mUpdateContact.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, "15685877938");
        getContentResolver().update(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, values,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, new String[]{"goudan"});
      }
    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case 1:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          readContacts();
        } else {
          Toast.makeText(this, "You denied", Toast.LENGTH_SHORT).show();
        }
        break;
      case 2:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          writeContact();
        }else {
          Toast.makeText(this, "You denied wtite contact", Toast.LENGTH_SHORT).show();
        }
      default:
    }
  }

}