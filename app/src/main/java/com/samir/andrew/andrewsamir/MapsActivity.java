package com.samir.andrew.andrewsamir;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samir.andrew.andrewsamir.Data.NearestData;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // get the data from shared pref and put them on the map
        SharedPreferences prefs = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE);
        ArrayList<NearestData> nearestDataArrayList = new ArrayList<>();
        Gson gson = new Gson();
        String json = prefs.getString("MyObject", "");
        nearestDataArrayList = gson.fromJson(json, new TypeToken<ArrayList<NearestData>>() {
        }.getType());

        for (int i = 0; i == nearestDataArrayList.size(); i++) {
            addToMap(nearestDataArrayList.get(i).getTitle(),
                    Double.parseDouble(nearestDataArrayList.get(i).getLat()),
                    Double.parseDouble(nearestDataArrayList.get(i).getLon()));
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                // intent to details acticity
                // set the pressed marker data to singletone class

               return false;
            }
        });

    }

    private void addToMap(String title, Double lat, Double lan) {
        Marker m1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lan))
                .anchor(0.5f, 0.5f)
                .title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(m1.getPosition(), 15));

    }
}
