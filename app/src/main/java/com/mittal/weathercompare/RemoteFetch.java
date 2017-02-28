package com.mittal.weathercompare;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SatyamMittal on 28-11-2016.
 */
public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&APPID=ed9cbe6c6548f60b292dec448e36e08e";

    public static JSONObject getJSON(Context context, String city){

        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            Log.d("sasa",url.toString());
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());
            Log.d("sasa",data.toString());
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }

}

