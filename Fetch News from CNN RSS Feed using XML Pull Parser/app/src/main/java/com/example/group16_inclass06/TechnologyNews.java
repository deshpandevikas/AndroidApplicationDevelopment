package com.example.group16_inclass06;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class TechnologyNews extends AppCompatActivity implements TechAsyncTask.IData, GetImageAsync.ImageDataIntf {
    public static ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_news);

        new TechAsyncTask(this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
        progressDialog = new ProgressDialog(TechnologyNews.this);
        progressDialog.setTitle("Loading News... ");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void setnewsdata(final ArrayList<Technology> newslist) {
        progressDialog.dismiss();
        ScrollView scr = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout item = (LinearLayout) scr.findViewById(R.id.linlayout);

        for (int i = 0; i < newslist.size(); i++) {
            View child = getLayoutInflater().inflate(R.layout.newsitem, null);
            child.setTag(newslist.get(i));
            TextView a = (TextView) child.findViewById(R.id.textView);
            a.setText(newslist.get(i).getTitle());
            ImageView iv = (ImageView) child.findViewById(R.id.imageView);
            new GetThumbImage(iv).execute(newslist.get(i).getThumbUrl());
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TechnologyNews.this, NewsDetails.class);
                    Technology tech = (Technology) v.getTag();
                    intent.putExtra("title", tech.getTitle());
                    intent.putExtra("pubdate", tech.getPubDate().toString());
                    intent.putExtra("imageUrl", tech.getImageUrl());
                    intent.putExtra("description", tech.getDescription());
                    startActivity(intent);
                }
            });
            item.addView(child);
        }
    }

    @Override
    public void setImageDataView(Bitmap bitmapImage) {

    }
}
