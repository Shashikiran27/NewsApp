package com.example.shashikiran.newsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.AsyncTask;


import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterOnClickHandler {

    private TextView mSearchResultsTextView;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<NewsItems> articlesList = new ArrayList<>();
    private ProgressDialog pdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override

    public void onClick(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    private void makeNewsAppQuery() {
        URL newsAppSearchUrl = NetworkUtils.buildUrl();
        new NewsAppQueryTask().execute(newsAppSearchUrl);
    }

    public class NewsAppQueryTask extends AsyncTask<URL, Void, ArrayList<NewsItems>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(MainActivity.this);
            pdialog.setMessage("Loading data...");
            pdialog.setIndeterminate(false);
            pdialog.show();
        }

        @Override
        protected ArrayList<NewsItems> doInBackground(URL... params) {
            String jsonResults;
            ArrayList<NewsItems> results = null;
            try {
                jsonResults = NetworkUtils.getResponseFromHttpUrl(params[0]);
                results = NetworkUtils.parseJSON(jsonResults);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return results;
        }


        @Override
        protected void onPostExecute(final ArrayList<NewsItems> data) {

            if(data != null){
                mNewsAdapter.setNewsData(data);
            }
            pdialog.dismiss();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_search) {
            new NewsAppQueryTask().execute(NetworkUtils.buildUrl());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
