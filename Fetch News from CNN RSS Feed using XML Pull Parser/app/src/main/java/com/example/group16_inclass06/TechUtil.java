package com.example.group16_inclass06;

import android.util.EventLog;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Satish on 9/26/2016.
 */
public class TechUtil {

    static public class PullParse {

        static ArrayList<Technology> parsePull(InputStream ins) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(ins, "UTF-8");
            ArrayList<Technology> techList = new ArrayList<Technology>();
            Technology tech = null;

            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            tech = new Technology();
                        } else if (parser.getName().equals("title")) {
                            if (tech != null) {
                                tech.setTitle(parser.nextText().trim());
                            }
                        } else if (parser.getName().equals("link")) {
                            if (tech != null) {
                                tech.setLink(parser.nextText().trim());
                            }
                        } else if (parser.getName().equals("pubDate")) {
                            if (tech != null) {

                                String date = parser.nextText().trim();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                                Date convertedDate = new Date();
                                try {
                                    convertedDate = dateFormat.parse(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                tech.setPubDate(convertedDate);
                            }
                        } else if (parser.getName().equals("media:content")) {
                            if (parser.getAttributeValue(null, "height").equals(parser.getAttributeValue(null, "width"))) {
                                if (tech != null && tech.getThumbUrl() == null) {
                                    tech.setThumbUrl(parser.getAttributeValue(null, "url").trim());
                                }
                            } else {
                                if (tech != null && tech.getImageUrl() == null) {
                                    tech.setImageUrl(parser.getAttributeValue(null, "url").trim());
                                }
                            }
                        } else if (parser.getName().equals("description")) {
                            if (tech != null) {
                                tech.setDescription(parser.nextText().trim());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            techList.add(tech);
                            tech = null;
                        }
                        break;
                    default:
                        break;
                }

                event = parser.next();
            }
            return techList;
        }
    }
}
