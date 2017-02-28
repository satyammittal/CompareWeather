package com.mittal.weathercompare;

/**
 * Created by SatyamMittal on 28-11-2016.
 */
import android.app.Activity;
import android.content.SharedPreferences;

public class CityPreference {

    SharedPreferences prefs;

    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    String getCity(){
        return prefs.getString("city", "Kaithal");
    }
    String getCity2(){
        return prefs.getString("city2", "Kaithal");
    }

    void setCity(String city,String city2){
        prefs.edit().putString("city", city).commit();prefs.edit().putString("city2", city2).commit();
    }

}

