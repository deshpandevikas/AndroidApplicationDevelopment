package com.example.group16_inclass06;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetails extends AppCompatActivity implements GetImageAsync.ImageDataIntf {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        TextView a1 = (TextView) findViewById(R.id.tvstorytitle);
        TextView a2 = (TextView) findViewById(R.id.tvdate);
        TextView a4 = (TextView) findViewById(R.id.descdetails);
        new GetImageAsync(this).execute(getIntent().getStringExtra("imageUrl"));

        a1.setText(getIntent().getStringExtra("title"));

        String date = getIntent().getStringExtra("pubdate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            date = dateFormat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        a2.setText(date);
        a4.setText(getIntent().getStringExtra("description"));
    }

    @Override
    public void setImageDataView(Bitmap bitmapImage) {
        ImageView a3 = (ImageView) findViewById(R.id.imageview);
        a3.setImageBitmap(bitmapImage);
    }
}
