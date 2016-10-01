package com.example.group16_inclass06;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Satish on 9/25/2016.
 */
public class GetImageAsync extends AsyncTask<String, Void, Bitmap> {

    ImageDataIntf imageDataIntf;

    public GetImageAsync(ImageDataIntf imageDataIntf) {
        this.imageDataIntf = imageDataIntf;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap != null) {
            imageDataIntf.setImageDataView(bitmap);
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        InputStream is = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(is);
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public interface ImageDataIntf {
        public void setImageDataView(Bitmap bitmapImage);
    }

}
