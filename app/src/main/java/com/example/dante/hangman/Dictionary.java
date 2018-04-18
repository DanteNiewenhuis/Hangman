package com.example.dante.hangman;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Dictionary {
    private ArrayList<String> words = new ArrayList<>();


    public Dictionary (Context context) throws IOException, XmlPullParserException {
        XmlResourceParser xpp = context.getResources().getXml(R.xml.dictionary);
        while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
            if (xpp.getEventType()==XmlPullParser.START_TAG) {
                if (xpp.getName().equals("item")) {
                    xpp.next();
                    words.add(xpp.getText());
                }
            }

            xpp.next();
        }
    }

    public String new_word() {
        int rnd = new Random().nextInt(words.size());
        return words.get(rnd);
    }
}
