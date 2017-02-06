package com.example.myapplication;

import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = this.getAssets();
        try {
            InputStream inputStream = assetManager.open("new.xml");


            List<Voice> voices = getdata(inputStream);
            for (Voice v : voices) {
                Log.e("MainActivity", "v=" + v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public List<Voice> getdata(InputStream inputStream) throws XmlPullParserException, IOException {
        List<Voice> voices = new ArrayList<>();

        XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
        xmlPullParser.setInput(inputStream, "GB2312");
        int eventType = xmlPullParser.getEventType();
        Voice voice = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if ("voice".equals(xmlPullParser.getName())) {
                    voice = new Voice();
                    voice.id = xmlPullParser.getAttributeValue(0);
                }
                if (voice != null && "name".equals(xmlPullParser.getName())) {
                    voice.name = xmlPullParser.nextText();
                }
                if (voice != null && "age".equals(xmlPullParser.getName())) {
                    voice.age = xmlPullParser.nextText();
                }

            } else if (eventType == XmlPullParser.END_TAG) {
                if ("voice".equals(xmlPullParser.getName())) {
                    voices.add(voice);
                }
            }
            eventType = xmlPullParser.next();
        }
        return voices;
    }


}
