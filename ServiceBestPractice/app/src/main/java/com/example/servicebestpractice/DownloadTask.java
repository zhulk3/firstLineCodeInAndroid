package com.example.servicebestpractice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import android.os.AsyncTask;
import android.os.Environment;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {
  public static final int TYPE_SUCCESS = 0;
  public static final int TYPE_FAILED = 1;
  public static final int TYPE_PAUSED = 2;
  public static final int TYPE_CANCELED = 3;

  private DownloadListener mListener;
  private boolean isCanceled = false;
  private boolean isPaused = false;
  private int lastProgress;

  public DownloadTask(DownloadListener listener) {
    mListener = listener;
  }


  @Override
  protected void onProgressUpdate(Integer... values) {
    int progress = values[0];
    if (progress > lastProgress) {
      mListener.onProgress(progress);
    }
    lastProgress = progress;
  }

  @Override
  protected void onPostExecute(Integer status) {
    switch (status) {
      case TYPE_SUCCESS:
        mListener.onSuccess();
        break;
      case TYPE_CANCELED:
        mListener.onCanceled();
        break;
      case TYPE_FAILED:
        mListener.onFailed();
        break;
      case TYPE_PAUSED:
        mListener.onPaused();
        break;
      default:
        break;
    }

  }

  @Override
  protected Integer doInBackground(String... strings) {
    InputStream inputStream = null;
    RandomAccessFile savedFile = null;
    File file = null;
    try {
      long downloadedLength = 0;
      String downloadUrl = (String) strings[0];
      String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
      String directory =
          Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
      file = new File(directory+ fileName);
      if (file.exists()) {
        downloadedLength = file.length();
      }
      long contentLength = getContentLength(downloadUrl);
      if (contentLength == 0) {
        return TYPE_FAILED;
      } else if (contentLength == downloadedLength) {
        return TYPE_SUCCESS;
      }
      OkHttpClient okHttpClient = new OkHttpClient();
      Request request = new Request.Builder().addHeader("RANGE", "bytes=" + downloadedLength + "-")
          .url(downloadUrl).build();
      Response response = okHttpClient.newCall(request).execute();
      if (response != null) {
        inputStream = response.body().byteStream();
        savedFile = new RandomAccessFile(file, "rw");
        savedFile.seek(downloadedLength);
        byte[] b = new byte[1024];
        int total = 0;
        int len;
        while ((len = inputStream.read(b)) != -1) {
          if (isCanceled) {
            return TYPE_CANCELED;
          } else if (isPaused) {
            return TYPE_PAUSED;
          } else {
            total += len;
            savedFile.write(b, 0, len);
            int progress = (int) ((total + downloadedLength) * 100 / contentLength);
            publishProgress(progress);
          }
        }
        response.close();
        return TYPE_SUCCESS;
      }


    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (savedFile != null) {
        try {
          savedFile.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (isCanceled && file != null) {
        file.delete();
      }
    }
    return TYPE_FAILED;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  private long getContentLength(String downloadUrl) throws IOException {
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder().url(downloadUrl).build();
    Response response = okHttpClient.newCall(request).execute();
    if (response != null && response.isSuccessful()) {
      long contentLength = response.body().contentLength();
      response.close();
      return contentLength;
    }
    return 0;
  }

  public void pauseDownload() {
    isPaused = true;
  }

  public void cancelDownload() {
    isCanceled = true;
  }
}
