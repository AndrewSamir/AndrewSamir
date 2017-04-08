package com.samir.andrew.andrewsamir;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samir.andrew.andrewsamir.Data.NearestData;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE);
        ArrayList<NearestData> nearestDataArrayList = new ArrayList<>();
        Gson gson = new Gson();
        String json = prefs.getString("MyObject", "");
        nearestDataArrayList = gson.fromJson(json, new TypeToken<ArrayList<NearestData>>() {
        }.getType());

        // create list and add the arraylist to the list
        // onItemclicklistener intent to details activity

        Log.d("response",nearestDataArrayList.get(0).getTitle());
    }
}
