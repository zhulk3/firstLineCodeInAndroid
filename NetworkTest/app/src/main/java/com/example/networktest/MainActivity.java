package com.example.networktest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private TextView mTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button sendRequest = findViewById(R.id.send_request);
    mTextView = findViewById(R.id.response_text);

    sendRequest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        sendRequest();
        sendRequestWithOkhttp();
      }
    });

  }

  private void sendRequest() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        try {
          URL url = new URL("https://cn.bing.com/");
          httpURLConnection = (HttpURLConnection) url.openConnection();
          httpURLConnection.setReadTimeout(8000);
          httpURLConnection.setReadTimeout(8000);
          InputStream inputStream = httpURLConnection.getInputStream();
          bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
          StringBuilder stringBuilder = new StringBuilder();
          String line;
          while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
          }
          showResponse(stringBuilder.toString());
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (bufferedReader != null) {
            try {
              bufferedReader.close();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          if (httpURLConnection != null) {
            httpURLConnection.disconnect();
          }

        }
      }
    }).start();

  }

  private void showResponse(final String string) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        mTextView.setText(string);
      }
    });
  }

  private void sendRequestWithOkhttp() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          OkHttpClient client = new OkHttpClient();
          Request request = new Request.Builder().url("http:///10.0.2.2/data_three.json").build();
          Response response = client.newCall(request).execute();
          String string = response.body().string();
//          showResponse(string);
//          parseXMLWithPull(string);
//          parseXMLWithSAX(string);
//          parseJSONWithJSONObject(string);
          parseJSONWithGSON(string);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private void parseXMLWithPull(String xmlData) {
    try {
      XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
      XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
      xmlPullParser.setInput(new StringReader(xmlData));
      int eventType = xmlPullParser.getEventType();
      String id = "";
      String name = "";
      String version = "";
      int a = 2;
      while (eventType != XmlPullParser.END_DOCUMENT) {
        String nodeName = xmlPullParser.getName();
        switch (eventType) {
          case XmlPullParser.START_TAG: {
            if ("id".equals(nodeName)) {
              id = xmlPullParser.nextText();
            } else if ("name".equals(nodeName)) {
              name = xmlPullParser.nextText();
            } else if ("version".equals(nodeName)) {
              version = xmlPullParser.nextText();
            }
            break;
          }
          case XmlPullParser.END_TAG: {
            if ("app".equals(nodeName)) {
              Log.d(TAG, "parseXMLWithPull: " + id);
              Log.d(TAG, "parseXMLWithPull: " + name);
              Log.d(TAG, "parseXMLWithPull: " + version);
            }
            break;
          }
          default:
            break;
        }
        eventType = xmlPullParser.next();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void parseXMLWithSAX(String xmlData){
    try {
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
      ContentHandle contentHandle = new ContentHandle();
      xmlReader.setContentHandler(contentHandle);
      xmlReader.parse(new InputSource(new StringReader(xmlData)));
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void parseJSONWithJSONObject(String jsonData){
    try {
      JSONArray jsonArray = new JSONArray(jsonData);
      for (int i = 0;i<jsonArray.length();i++){
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String version = jsonObject.getString("version");
        Log.d(TAG, "parseJSONWithJSONObject: "+id);
        Log.d(TAG, "parseJSONWithJSONObject: "+name);
        Log.d(TAG, "parseJSONWithJSONObject: "+version);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void parseJSONWithGSON(String jsonData,){
    Gson gson = new Gson();
    List<App>appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());

    for (App app:appList){
      Log.d(TAG, "parseJSONWithGSON: id"+app.getId());
      Log.d(TAG, "parseJSONWithGSON: name"+app.getName());
      Log.d(TAG, "parseJSONWithGSON: version"+app.getVersion());
    }
  }
}