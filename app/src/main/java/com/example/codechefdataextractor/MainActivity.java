package com.example.codechefdataextractor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVMovie;
    private AdapterData mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Make call to AsyncTask
        new AsyncFetch().execute();
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL Url = new URL("https://script.googleusercontent.com/macros/echo?user_content_key=6YXvEBCYsepWf4KiExOpGAD1V1OFCInopMwVrsiGaQ2ER9BoUfSj8YCLn0jpkJgizE8RmRJ_XpgBE1WjMR6B66fQsQWx5w7Fm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnL6CuIQ_jx64dUfXh81eCKWT5Ze72kewn8_XKIm4GKxoYfqeUKoWXJDM5cgc38rBPaV-rV9yhQ38&lib=MXHhDGVxHp761uy041AgyVZl9ogoAk9d_");
                HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();
                connection.disconnect();
                is.close();
                sb.delete(0, sb.length());
                return line;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<Data> data=new ArrayList<>();

            pdLoading.dismiss();
            try {
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                JSONArray jArray = new JSONArray(result);
                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
//                    Toast.makeText(MainActivity.this, "aman", Toast.LENGTH_LONG).show();
                    JSONObject json_data = jArray.getJSONObject(i);
                    Data movieData = new Data();
                    movieData.Id= json_data.getString("Id");
                    movieData.Title= json_data.getString("Title");
                    movieData.Genere= json_data.getString("Genere");
                    movieData.Url= json_data.getString("Url");
                    movieData.Rating= json_data.getDouble("rating");
                    data.add(movieData);
                }

                // Setup and Handover data to recyclerview
                mRVMovie = (RecyclerView)findViewById(R.id.movieList);
                mAdapter = new AdapterData(MainActivity.this, data);
                mRVMovie.setAdapter(mAdapter);
                mRVMovie.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }
}