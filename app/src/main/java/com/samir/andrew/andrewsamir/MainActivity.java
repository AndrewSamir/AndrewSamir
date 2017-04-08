package com.samir.andrew.andrewsamir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.samir.andrew.andrewsamir.Data.NearestData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String CLIENT_ID;
    private String CLIENT_SECRET;

    ArrayList<NearestData> nearestDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CLIENT_ID = getResources().getString(R.string.CLIENT_ID);
        CLIENT_SECRET = getResources().getString(R.string.CLIENT_SECRET);

        nearestDataArrayList = new ArrayList<>();
        Pair<String, String> location = getLocation();

        if (location != null) {

            getDataFromFoursquare("https://api.foursquare.com/v2/venues/search?client_id="
                    + CLIENT_ID
                    + "&client_secret="
                    + CLIENT_SECRET
                    + "&v=20170405&ll="
                    + location.first
                    + ","
                    + location.second
                    + "&radius=100&categoryId=4d4b7105d754a06374d81259");
        } else
            Toast.makeText(MainActivity.this, "GPS error", Toast.LENGTH_SHORT).show();

    }

    private void getDataFromFoursquare(String url) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseData", response);

                        ParsingJsonData(response);

                        // set the data and send it to firebase
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // get the data from firebase
                Log.d("responseData", error.toString());

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void ParsingJsonData(String jsonData) {
        try {
            JSONObject jsonobjectAll = new JSONObject(jsonData);
            JSONObject responseJSON = jsonobjectAll.getJSONObject("response");
            JSONArray jsonArray = responseJSON.getJSONArray("venues");
            for (int i = 0; i == jsonArray.length(); i++) {
                JSONObject CafeData = jsonArray.getJSONObject(i);
                JSONArray categories = CafeData.getJSONArray("categories");
                JSONObject firstElement = categories.getJSONObject(0);
                String rating;
                try {
                    rating = CafeData.getString("rating");
                } catch (JSONException e) {
                    rating = "No Rate Available";

                }

                String phone;

                try {

                    JSONObject jsonObject = CafeData.getJSONObject("contact");
                    phone = jsonObject.getString("phone");
                } catch (JSONException e) {
                    phone = "No Phone Available";
                }

                JSONObject locationarray = CafeData.getJSONObject("location");
                JSONArray formattedAddress = locationarray.getJSONArray("formattedAddress");
                JSONArray labeledLatLngsArray = locationarray.getJSONArray("labeledLatLngs");
                JSONObject latObject = labeledLatLngsArray.getJSONObject(0);

                nearestDataArrayList.add(new NearestData(CafeData.getString("name"),
                        firstElement.getString("name"),
                        firstElement.getString("pluralName"),
                        rating,
                        phone, formattedAddress.getString(0),
                        latObject.getString("lat"),
                        latObject.getString("lng")));

            }
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE).edit();
            Gson gson = new Gson();
            String json = gson.toJson(nearestDataArrayList);
            editor.putString("MyObject", json);
            editor.commit();

            startActivity(new Intent(MainActivity.this, ListData.class));
        } catch (JSONException e) {
            Log.d("responseData", "error in JsonObject");

            e.printStackTrace();
        }


    }

    private Pair<String, String> getLocation() {
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            String stringLongitude = String.valueOf(gpsTracker.longitude);
            if (stringLatitude.equals("0.0"))
                stringLatitude = "30.094586";
            if (stringLongitude.equals("0.0"))
                stringLongitude = "31.304528";
            return new Pair<>(stringLatitude, stringLongitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
            return null;

        }

    }


}



