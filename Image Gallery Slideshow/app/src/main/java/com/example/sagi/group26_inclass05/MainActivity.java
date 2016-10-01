package com.example.sagi.group26_inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetImg.Idata{
    String search = null;
    String result_urls = null;
    int index = 0;
    ProgressDialog progress;
    ArrayList<String> splitUrls = new ArrayList<String>();
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button go = (Button) findViewById(R.id.buttonGo);
        final Spinner spinnersearch = (Spinner) findViewById(R.id.spinnersearch);

        ImageView imageViewnext = (ImageView) findViewById(R.id.imageView_next);
        ImageView imageViewprev = (ImageView) findViewById(R.id.imageView_prev);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersearch.setAdapter(adapter);

        spinnersearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                search = spinnersearch.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkConnected()) {
                    Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_LONG).show();
                    new GetURLs().execute("http://dev.theappsdr.com/apis/photos/index.php?keyword=" + search);

                    progress = new ProgressDialog(MainActivity.this);
                    progress.setTitle("Loading Dictionary");
                    progress.setCancelable(false);
                    progress.show();

                } else {
                    Toast.makeText(getApplicationContext(), "Internet Not Connected", Toast.LENGTH_LONG).show();
                }


            }
        });

         imageViewnext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 index++;
                 if(index < splitUrls.size()){

                     new GetImg(MainActivity.this).execute(splitUrls.get(index));
                 }
                 else {
                     index = 0;
                     new GetImg(MainActivity.this).execute(splitUrls.get(index));
                 }
             }
         });

         imageViewprev.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 index--;
                 if (index < 0){
                     index = splitUrls.size()-1;
                     new GetImg(MainActivity.this).execute(splitUrls.get(index));
                 }
                 else {
                     new GetImg(MainActivity.this).execute(splitUrls.get(index));
                 }

             }
         });
    }

    public Boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        } else
            return false;
    }

    @Override
    public void setupData(Bitmap bit) {
        ImageView imageViewgallery = (ImageView) findViewById(R.id.imageView_gallery);
        progress.dismiss();
        imageViewgallery.setImageBitmap(bit);
    }

    private class GetURLs extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);

        if(s!= null){
            result_urls =  s;
            splitUrls.clear();
            for(String st : result_urls.split(";")){
                if(!st.equalsIgnoreCase(search)) {
                    splitUrls.add(st);
                    Log.d("demo", st);

                }
            }
            new GetImg(MainActivity.this).execute(splitUrls.get(index));
        }
        else {

            Toast.makeText(getApplicationContext(),"No images found", Toast.LENGTH_LONG).show();
        }
    }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);


        }
    }
}
