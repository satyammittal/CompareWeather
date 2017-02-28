package com.mittal.weathercompare;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.TimedText;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */

public class WeatherActivityFragment extends Fragment {

    Typeface weatherFont;

    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView humidityField;
    TextView pressureField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    View root;
    String url1="www.bing.com";
    String url2="www.bing.com";
    Handler handler;
    int count=1;
    int test=0;
    public WeatherActivityFragment(){
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        root=rootView;
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        humidityField = (TextView)rootView.findViewById(R.id.humidity_field);
        pressureField = (TextView)rootView.findViewById(R.id.pressure_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);

        weatherIcon.setTypeface(weatherFont);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        updateWeatherData(new CityPreference(getActivity()).getCity());
    }

    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }
    private void updateWeatherData2(final String city){
        cityField = (TextView)root.findViewById(R.id.city_field2);
        updatedField = (TextView)root.findViewById(R.id.updated_field2);
        detailsField = (TextView)root.findViewById(R.id.details_field2);
        humidityField=(TextView) root.findViewById(R.id.humidity_field2);
        pressureField=(TextView) root.findViewById(R.id.pressure_field2);
        currentTemperatureField = (TextView)root.findViewById(R.id.current_temperature_field2);
        weatherIcon = (TextView)root.findViewById(R.id.weather_icon2);

        weatherIcon.setTypeface(weatherFont);

        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);


                        }
                    });
                }
            }
        }.start();
    }
    public void s1(){
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setData(Uri.parse(url1));
        startActivity(myWebLink);

    }
    public void s2(){
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setData(Uri.parse(url2));
        startActivity(myWebLink);

    }
    private void makenews(JSONObject json,String city){
        String city1=new CityPreference(getActivity()).getCity();
        String city2=new CityPreference(getActivity()).getCity2();
        try {
            Log.d("sss",json.toString());
            JSONArray d=json.getJSONArray("value");
            Log.d("sss",d.toString());
            JSONObject d2=d.getJSONObject(0);
            String n=d2.getString("name");
            Button t = (Button) root.findViewById(R.id.news1);
            if(city1.equals(city))
            {
                 t = (Button) root.findViewById(R.id.news1);
                 url1=d2.getString("url");
            }
            else {
                 t = (Button) root.findViewById(R.id.news2);
                 url2=d2.getString("url");
            }
            t.setText(n);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US));
            humidityField.setText(main.getString("humidity") + "%");
            pressureField.setText(main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ (char) 0x00B0+ "C");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText(updatedOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);
            if(count==1){
                updatecity(new CityPreference(getActivity()).getCity());
                updateWeatherData2(new CityPreference(getActivity()).getCity2());
                count--;
            }
            else{
                test=1;
                updatecity(new CityPreference(getActivity()).getCity2());
            }
        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }
    private void updatecity(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = FetchNews.getNews(city);
                Log.d("aas","bdds"+json);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            makenews(json,city);
                        }
                    });
                }
            }
        }.start();
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

    public void changeCity(String city){
        updateWeatherData(city);
    }
}

