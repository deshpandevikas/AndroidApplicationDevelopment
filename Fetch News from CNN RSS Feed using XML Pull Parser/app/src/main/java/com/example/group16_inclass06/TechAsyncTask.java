package com.example.group16_inclass06;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Satish on 9/26/2016.
 */
public class TechAsyncTask extends AsyncTask<String, Void, ArrayList<Technology>> {

    IData activity;

    public TechAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<Technology> technologies) {
        super.onPostExecute(technologies);

        Collections.sort(technologies, Technology.revComp);
        activity.setnewsdata(technologies);
    }

    @Override
    protected ArrayList<Technology> doInBackground(String... params) {

        URL url = null;
        try {
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream in = connection.getInputStream();
                return TechUtil.PullParse.parsePull(in);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public interface IData {

        public void setnewsdata(ArrayList<Technology> newslist);
    }
}
