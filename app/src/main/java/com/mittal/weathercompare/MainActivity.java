package com.mittal.weathercompare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class MainActivity extends AppCompatActivity {
    String TAG="error";
    String city1="none";
    String city2="none";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                city1=place.getName().toString();
                Log.i(TAG,place.getAddress().toString());
                String a[]=place.getAddress().toString().split(",");
                int len=a.length;
                String h=city1;
                if(len>=3) {
                    h = a[len - 3];
                }
                city1=h;

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("city",place.toString());
                Log.i(TAG, "Place: " + place.getName());
                city2=place.getName().toString();
                String a[]=place.getAddress().toString().split(",");
                int len=a.length;
                String h=city2;
                if(len>=3) {
                    h = a[len - 3];
                }
                city2=h;

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
    public void checkdiff(View view){
        Intent a=new Intent(this,WeatherActivity.class);
        a.putExtra("city1",city1);
        a.putExtra("city2",city2);
        startActivity(a);

    }
}
