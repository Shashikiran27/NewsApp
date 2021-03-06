package com.example.shashikiran.newsapp;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class NetworkUtils {

    final static String NEWSAPI_BASE_URL = "https://newsapi.org/v1/articles";

    final static String PARAM_SOURCE = "source";

    final static String source = "the-next-web";

    final static String PARAM_SORT = "sortBy";

    final static String sortBy = "latest";

    final static String PARAM_APIKEY = "apiKey";

    final static String APIKeyValue = "388c8c25d06b41768409d95bfa6431a9";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(NEWSAPI_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, source)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(PARAM_APIKEY, APIKeyValue)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }




    public static ArrayList<NewsItems> parseJSON(String json) throws JSONException {

        ArrayList<NewsItems> results = new ArrayList<>();
        JSONObject resultsJson = new JSONObject(json);
        JSONArray arrayOfArticles = resultsJson.getJSONArray("articles");

        for(int i = 0; i < arrayOfArticles.length(); i++){

            JSONObject item = arrayOfArticles.getJSONObject(i);
            String title = item.getString("title");
            String author = item.getString("author");
            String description = item.getString("description");
            String publishedAt = item.getString("publishedAt");
            String url = item.getString("url");

            NewsItems ni = new NewsItems(title, author, description, publishedAt, url);

            results.add(ni);

        }

        return results;

    }




}
