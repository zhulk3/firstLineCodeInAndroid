package com.example.cameraalbumtest;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  public static final int TAKE_PHOTO = 1;
  public static final int CHOOSE_PHOTO = 2;
  private ImageView mImageView;
  private Uri mImageUri;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button takePhoto = findViewById(R.id.take_photo);
    mImageView = findViewById(R.id.picture);

    takePhoto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
          if (outputImage.exists()) {
            outputImage.delete();
          }
          outputImage.createNewFile();
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
          mImageUri = FileProvider
              .getUriForFile(MainActivity.this, "com.example.cameraalbumtest.fileprovider",
                  outputImage);
        } else {
          mImageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, TAKE_PHOTO);
        Log.d(TAG, "onClick: ");
      }
    });

    Button choosePhoto = findViewById(R.id.choose_photo);
    choosePhoto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (ActivityCompat
            .checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(MainActivity.this,
              new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
          openAlbum();
        }

      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case TAKE_PHOTO:
        if (resultCode == RESULT_OK) {
          try {
            Bitmap bitmap =
                BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
            mImageView.setImageBitmap(bitmap);
            Log.d(TAG, "onActivityResult: " + bitmap.getHeight());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        break;
      case CHOOSE_PHOTO:
        if (resultCode == RESULT_OK) {
          if (Build.VERSION.SDK_INT >= 19) {
            handleImageOnKitKat(data);
          } else {
            handleImageBeforeKitKat(data);
          }

        }
      default:
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case 1:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          openAlbum();
        } else {
          Toast.makeText(this, "You Denied", Toast.LENGTH_SHORT).show();
        }
        break;
      default:
    }
  }

  private void openAlbum() {
    Intent intent = new Intent("android.intent.action.GET_CONTENT");
    intent.setType("image/*");
    startActivityForResult(intent, CHOOSE_PHOTO);
  }

  @TargetApi(19)
  private void handleImageOnKitKat(Intent data) {
    String imagePath = null;
    Uri uri = data.getData();
    if (DocumentsContract.isDocumentUri(this, uri)) {
      String docId = DocumentsContract.getDocumentId(uri);
      if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
        String id = docId.split(":")[1];
        String selection = MediaStore.Images.Media._ID + "=" + id;
        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
      } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
        Uri contentUri = ContentUris
            .withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
        imagePath = getImagePath(contentUri, null);
      }
    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
      imagePath = getImagePath(uri, null);
    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
      imagePath = uri.getPath();
    }
    disPlayImage(imagePath);
    Log.d(TAG, "disPlayImage: "+imagePath);
  }

  private void handleImageBeforeKitKat(Intent data) {
    Uri uri = data.getData();
    String imagePath = getImagePath(uri, null);
    disPlayImage(imagePath);
  }

  private String getImagePath(Uri uri, String selection) {
    String path = null;
    Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

      }
      cursor.close();
    }
    return path;
  }

  private void disPlayImage(String imagePath) {
    if (imagePath != null) {
      Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
      Log.d(TAG, "disPlayImage: "+imagePath);
      mImageView.setImageBitmap(bitmap);
    } else {
      Log.d(TAG, "disPlayImage: "+imagePath);
      Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
    }

  }

}