package com.mittal.weathercompare;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by SatyamMittal on 04-12-2016.
 */
public class FetchNews
{
    public static JSONObject getNews(String args)
    {

        try
        {
            Uri.Builder builder = Uri.parse("https://api.cognitive.microsoft.com/bing/v5.0/news/search").buildUpon();

            builder.appendQueryParameter("q", args);
            builder.appendQueryParameter("count", "10");
            builder.appendQueryParameter("offset", "0");
            builder.appendQueryParameter("mkt", "en-us");
            builder.appendQueryParameter("safeSearch", "Moderate");

            String uri = builder.build().toString();
            URL u=new URL(uri);
            URLConnection connection=u.openConnection();

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "63502f6d07cd4fc5846b5cc720a182da");


            // Request body
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            return data;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

