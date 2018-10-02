package com.example.shivam.newsapp1;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class NewsListLoader extends android.support.v4.content.AsyncTaskLoader<List<NewsList>> {

    private String requestUrlModified="";
    public static String request_URL = "https://content.guardianapis.com/search" ;

    public NewsListLoader(Context context, String s) {
        super(context);
        requestUrlModified = s;
    }
    @Override
    public List<NewsList> loadInBackground() {

        URL url = createUrl(requestUrlModified);
        String jsonResponse = "";

        try {
            HttpHandler sh = new HttpHandler();
            jsonResponse = sh.makeHttpRequest(url);
        } catch (IOException ignored) {
        }
        List<NewsList> newsList = extractFromJson(jsonResponse);
        return newsList;
    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    private List<NewsList> extractFromJson(String newsJSON) {

        List<NewsList> newsList_temp = new LinkedList<>();
        if(newsJSON != null) {
            try {

                JSONObject rootJsonObject = new JSONObject(newsJSON);
                JSONObject jsonObject1 = rootJsonObject.getJSONObject("response");
                JSONArray resultsJsonArray = jsonObject1.getJSONArray("results");

                if (resultsJsonArray.length() > 0) {
                    for (int i = 0; i < resultsJsonArray.length(); i++) {

                        JSONObject jsonObject = resultsJsonArray.getJSONObject(i);
                        String webTitle = jsonObject.getString("webTitle");
                        String webPublicationDate = jsonObject.getString("webPublicationDate");
                        String webUrl = jsonObject.getString("webUrl");
                        String sectionName = jsonObject.getString("sectionName");

                        JSONObject jsonObject_author = jsonObject.getJSONObject("fields");
                        String author = jsonObject_author.getString("byline");
                        if(author.equals(""))
                            author="anonymous";

                        String[] parts = webPublicationDate.split("T");
                        String part1 = parts[0];
                        String part2 = parts[1];

                        String[] sub_part2 = part2.split("Z");
                        String sub1_part2 = sub_part2[0];

                        newsList_temp.add(new NewsList(webTitle, sectionName, part1, sub1_part2, webUrl, author));
                    }
                }
            } catch (final JSONException e) {
                e.printStackTrace();
            }
            return newsList_temp;
        }
            return null;
    }
}
