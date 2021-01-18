package com.example.networktest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ContentHandle extends DefaultHandler {
  private static final String TAG = "ContentHandle";

  private String mNodeName;
  private StringBuilder mId;
  private StringBuilder mName;
  private StringBuilder mVersion;

  @Override
  public void startDocument() throws SAXException {
    mId = new StringBuilder();
    mName = new StringBuilder();
    mVersion = new StringBuilder();
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    mNodeName = localName;
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if ("id".equals(mNodeName)) {
      mId.append(ch, start, length);
    } else if ("name".equals(mNodeName)) {
      mName.append(ch, start, length);
    } else if ("version".equals(mNodeName)) {
      mVersion.append(ch, start, length);
    }
  }

  @Override
  public void endDocument() throws SAXException {
    super.endDocument();
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    Log.d(TAG, "parseXMLWithSAX: " + mId.toString().trim());
    Log.d(TAG, "parseXMLWithSAX: " + mName.toString().trim());
    Log.d(TAG, "parseXMLWithSAX: " + mVersion.toString().trim());
    mId.setLength(0);
    mName.setLength(0);
    mVersion.setLength(0);
  }
}
